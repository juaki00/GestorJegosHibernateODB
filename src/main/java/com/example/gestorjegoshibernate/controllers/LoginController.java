package com.example.gestorjegoshibernate.controllers;

import com.example.gestorjegoshibernate.App;
import com.example.gestorjegoshibernate.Session;
import com.example.gestorjegoshibernate.domain.HibernateUtils;
import com.example.gestorjegoshibernate.domain.usuario.User;
import com.example.gestorjegoshibernate.domain.usuario.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
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
            User u = new UserDAO().validateUser(user,pass);
            if(u == null){
                info.setText("Usuario no encontrado");
                info.setStyle("-fx-background-color: red; -fx-text-fill: white");
            }
            else {
                info.setText("Usuario " + user + " Pass " + pass);
                info.setStyle("-fx-background-color: green; -fx-text-fill: white");

                Session.setCurrentUser(u);
                /*Guardar usuario en sesion e ir a la proxima ventana*/
                try{
                    App.changeScene("main-view.fxml","Coleccion de videojuegos");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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

//        Configuration cfg = new Configuration();
//        cfg.configure();  //para probar hibernate solo estas dos linea
//
//        SessionFactory sf = cfg.buildSessionFactory(); //para probar la conexion
//
//        HibernateUtils.getSessionFactory(); //segunda comprobacion

//        UserDAO userdao = new UserDAO();
//        userdao.validateUser("usuario1","1234");

    }



}