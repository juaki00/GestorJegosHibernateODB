package com.example.gestorjegoshibernate.controllers;

import com.example.gestorjegoshibernate.App;
import com.example.gestorjegoshibernate.Session;
import com.example.gestorjegoshibernate.domain.HibernateUtils;
import com.example.gestorjegoshibernate.domain.juego.Game;
import com.example.gestorjegoshibernate.domain.usuario.User;
import com.example.gestorjegoshibernate.domain.usuario.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable
{

    @javafx.fxml.FXML
    private MenuItem mCerrar;
    @javafx.fxml.FXML
    private MenuItem mVolver;
    @javafx.fxml.FXML
    private Label gameInfo;
    @javafx.fxml.FXML
    private Label labelTitulo;
    @javafx.fxml.FXML
    private TextField txtName;
    @javafx.fxml.FXML
    private TextField txtCategory;
    @javafx.fxml.FXML
    private TextField txtPlataforma;
    @javafx.fxml.FXML
    private TextField txtEstudio;
    @javafx.fxml.FXML
    private TextField txtEnterprise;
    @javafx.fxml.FXML
    private TextField txtFormat;
    @javafx.fxml.FXML
    private TextField txtGameStatus;
    @javafx.fxml.FXML
    private TextField txtBoxStatus;
    @javafx.fxml.FXML
    private ComboBox<User> comboUser;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerYear;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerPlayers;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Button btnVolver;


    @javafx.fxml.FXML
    public void cerrar(ActionEvent actionEvent) {
        try {
            App.changeScene("login-view.fxml","Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent){
        try {
            App.changeScene("main-view.fxml","Colección de videojuegos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
gameInfo.setText(Session.getCurrentGame().toString());
spinnerPlayers.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4, Math.toIntExact(Session.getCurrentGame().getPlayers()),1));
spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970,2100, Math.toIntExact(Session.getCurrentGame().getYear()),1));
comboUser.setConverter(new StringConverter<User>() {
    @Override
    public String toString(User user) {
        return (user!=null)?user.getUsername() : "";

    }

    @Override
    public User fromString(String s) {
        return null;
    }
});

txtName.setText(Session.getCurrentGame().getName());
txtCategory.setText(Session.getCurrentGame().getCategory());
txtBoxStatus.setText(Session.getCurrentGame().getBoxStatus());
txtEnterprise.setText(Session.getCurrentGame().getEnterprise());
txtGameStatus.setText(Session.getCurrentGame().getGameStatus());
txtFormat.setText(Session.getCurrentGame().getFormat());
txtPlataforma.setText(Session.getCurrentGame().getPlatform());
txtEstudio.setText(Session.getCurrentGame().getStudio());
labelTitulo.setText(Session.getCurrentGame().getName());
comboUser.setValue(Session.getCurrentUser());
comboUser.getItems().addAll(new UserDAO().getAll());
    }

    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) {
        if(!txtName.getText().isEmpty()) {
            Session.getCurrentGame().setName(txtName.getText());
        }
        try(org.hibernate.Session s = HibernateUtils.getSessionFactory().openSession()){
            Transaction t = s.beginTransaction();
            Game g = s.get(Game.class, Session.getCurrentGame().getId());
            g.setName(Session.getCurrentGame().getName());
            t.commit();
            Session.setCurrentGame(g);
        }
    }
}