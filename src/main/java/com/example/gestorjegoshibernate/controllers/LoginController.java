package com.example.gestorjegoshibernate.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Label info;



    @FXML
    public void login(ActionEvent actionEvent) {
        String user = txtUsuario.getText();
        String pass = txtPass.getText();

        if(user.length()<4 || pass.length()<4){
            info.setText("Introduce los datos");
            info.setStyle("-fx-background-color: red; -fx-text-fill: white");
        }else {
            info.setText("Usuario " + user + " Pass " + pass);
            info.setStyle("-fx-background-color: green; -fx-text-fill: white");
        }
    }

    @FXML
    public void cancelar(ActionEvent actionEvent) {
        txtPass.setText("");
        txtUsuario.setText("");
        info.setStyle("-fx-background-color: transparent;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Configuration cfg = new Configuration();
        cfg.configure();  //para probar hibernate solo estas dos linea

        SessionFactory sf = cfg.buildSessionFactory(); //para probar la conexion
    }
}