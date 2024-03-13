package com.example.login;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button btnSigninScreen;

    @FXML
    private Group groupSignIn, groupSignUp;

    @FXML
    private void initialize() {
        anchorPane.getStyleClass().add("pane");
        groupSignUp.setVisible(false);
        btnSigninScreen.requestFocus();
        //btnSigninScreen.setStyle("Label.css"); ??
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
}