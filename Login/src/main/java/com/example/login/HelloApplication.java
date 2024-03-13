package com.example.login;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @FXML
    private Button btnSigninScreen;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        /*
        Had to do requestFocus() here as at the time of initialize() controls are
        not yet ready to handle focus.
        https://stackoverflow.com/questions/12744542/requestfocus-in-textfield-doesnt-work
        */
        //btnSigninScreen.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}