package com.example.login;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private static ArrayList<User> userList = new ArrayList<User>();

    @FXML
    private PasswordField passFieldSU;

    @FXML
    private TextField txtUsernameSU, txtEmailSU;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Group groupSignIn, groupSignUp;

    // https://stackoverflow.com/questions/32268733/using-jackson-to-manually-parse-json
    @FXML
    private void initialize() throws IOException {
        // Sets id to last id in the json file + 1
        try {
            JsonParser jsonParser = new JsonFactory().createParser(new File("users.json"));

            int id = 0;
            while(jsonParser.nextToken() != null) {
                String name = jsonParser.getCurrentName();

                if ("id".equals(name)) {
                    jsonParser.nextToken();
                    id = Integer.parseInt(jsonParser.getText());
                }
            }
            User.setCurrentID(id + 1);
        } catch (Exception e) {
        System.out.println("I'm cooked");
        e.printStackTrace();
        System.exit(0);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("users.json");

        List<User> employeeList = objectMapper.readValue(file, new TypeReference<>(){});

        anchorPane.getStyleClass().add("pane");
        groupSignUp.setVisible(false);

    }

    @FXML
    private void loadSignIn() {
        groupSignUp.setVisible(false);
        groupSignIn.setVisible(true);
    }

    @FXML
    private void loadSignUp() {
        groupSignIn.setVisible(false);
        groupSignUp.setVisible(true);
    }

    public void signUpPressed(ActionEvent actionEvent) throws IOException {
        userList.add(new User(txtUsernameSU.getText(), txtEmailSU.getText(), passFieldSU.getText()));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        objectMapper.writeValue(new File("users.json"), userList);
    }
}