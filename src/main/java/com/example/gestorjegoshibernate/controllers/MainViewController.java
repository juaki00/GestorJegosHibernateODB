package com.example.gestorjegoshibernate.controllers;
import com.example.gestorjegoshibernate.App;
import com.example.gestorjegoshibernate.Session;
import com.example.gestorjegoshibernate.domain.juego.GameDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{
    @javafx.fxml.FXML
    private Label info;
    @javafx.fxml.FXML
    private Button btnSalir;


    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        try{
            App.changeScene("login-view.fxml", "login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        info.setText((Session.getCurrentUser().toString()));
        new GameDAO().getAllFromUser(Session.getCurrentUser()).forEach(System.out::println);
//        System.out.println("hola");
//        System.out.println(Session.getCurrentUser().getUsername());
//        System.out.println("Adios");
    }

}