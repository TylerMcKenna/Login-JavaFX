package com.example.login;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HelloController {
    private static final String UNICODE_FORMAT = "UTF-8";
    private static ArrayList<User> userList = new ArrayList<User>();
    @FXML
    private Label lblWelcome;

    @FXML
    private PasswordField passFieldSU, passFieldSI;

    @FXML
    private TextField txtUsernameSU, txtEmailSU, txtEmailSI;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Group groupSignIn, groupSignUp;

    // creates new styleClass for anchorPanes in Label.css
    @FXML
    private void initialize() throws IOException {
        anchorPane.getStyleClass().add("pane");
        groupSignUp.setVisible(false);

    }

    // changes page
    @FXML
    private void loadSignIn() {
        groupSignUp.setVisible(false);
        groupSignIn.setVisible(true);
    }

    // changes page
    @FXML
    private void loadSignUp() {
        groupSignIn.setVisible(false);
        groupSignUp.setVisible(true);
    }

    // On sign in pressed, loads the JSON user file and tests if the entered information matches an account
    public void signInPressed(ActionEvent actionEvent) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
        User[] users = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = "users.json".toString();
            users = objectMapper.readValue(new File("users.json"), User[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < users.length; i++) {
            Cipher cipher = Cipher.getInstance("AES");
            if (Objects.equals(User.decryptString(users[i].getEncryptedEmail(), users[i].getKey(), cipher), txtEmailSI.getText())) {
                User temp = new User(users[i].getKey(), "noName", passFieldSI.getText(), txtEmailSI.getText(), users[i].getSalt());
                if (Objects.equals(temp.getHashedPassword(), users[i].getHashedPassword())) {
                    lblWelcome.setText("Login Successful, hello " + User.decryptString(users[i].getEncryptedUsername(), users[i].getKey(), cipher) + "!");
                } else {
                    lblWelcome.setText("Login failed.");
                }
                lblWelcome.setVisible(true);
            }
        }
    }

    // https://stackoverflow.com/questions/32268733/using-jackson-to-manually-parse-json
    // On sign up pressed adds user to json list from UI fields
    public void signUpPressed(ActionEvent actionEvent) throws IOException {
        userList.add(new User(txtUsernameSU.getText(), passFieldSU.getText(), txtEmailSU.getText()));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.writeValue(new File("users.json"), userList);
    }
}