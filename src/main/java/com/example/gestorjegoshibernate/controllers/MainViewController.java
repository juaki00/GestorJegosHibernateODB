package com.example.gestorjegoshibernate.controllers;
import com.example.gestorjegoshibernate.App;
import com.example.gestorjegoshibernate.Session;
import com.example.gestorjegoshibernate.domain.juego.Game;
import com.example.gestorjegoshibernate.domain.juego.GameDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{
    @javafx.fxml.FXML
    private Label info;
    @javafx.fxml.FXML
    private TableView<Game> tabla;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cNombre;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cPlataforma;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cCategoria;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cAnio;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cEstudio;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cFormato;
    @javafx.fxml.FXML
    private MenuBar menu;
    @javafx.fxml.FXML
    private Label titulo;
    @javafx.fxml.FXML
    private Menu mSalir;
    @javafx.fxml.FXML
    private Label lTotal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        info.setText("Bienvenido "+(Session.getCurrentUser().getUsername()));
        titulo.setText("Juegos de "+Session.getCurrentUser().getUsername());
        lTotal.setText("Numero de juegos: "+Session.getCurrentUser().getGames().size());

        cAnio.setCellValueFactory((fila)->{
            return new SimpleStringProperty(fila.getValue().getYear().toString());
        });

        cNombre.setCellValueFactory((fila)->{
            return new SimpleStringProperty(fila.getValue().getName());
        });

        cCategoria.setCellValueFactory((fila)->{
            return new SimpleStringProperty(fila.getValue().getCategory());
        });

        cEstudio.setCellValueFactory((fila)->{
            return new SimpleStringProperty(fila.getValue().getStudio());
        });

        cFormato.setCellValueFactory((fila)->{
            return new SimpleStringProperty(fila.getValue().getFormat());
        });

        cPlataforma.setCellValueFactory((fila)->{
            return new SimpleStringProperty(fila.getValue().getPlatform());
        });

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> observableValue, Game game, Game t1) {
                Session.setCurrentGame(t1);
                try {
                    App.changeScene("game-view.fxml",t1.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tabla.getItems().addAll( Session.getCurrentUser().getGames());

//        Session.getCurrentUser().getGames().forEach(System.out::println);
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        try{
            App.changeScene("login-view.fxml", "login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}