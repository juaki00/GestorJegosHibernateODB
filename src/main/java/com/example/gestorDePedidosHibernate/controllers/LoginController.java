package com.example.gestorDePedidosHibernate.controllers;
import com.example.gestorDePedidosHibernate.App;
import com.example.gestorDePedidosHibernate.domain.usuario.UsuarioDAO;
import com.example.gestorDePedidosHibernate.domain.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Data;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


@Data
public class LoginController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private Button btnEntrar;
    @FXML
    private PasswordField tfPass;
    @FXML
    private Label info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void btnEntrar() {
        UsuarioDAO dao = new UsuarioDAO();
        try{
            if (dao.isCorrectUser(tfUsuario.getText(), tfPass.getText())) {
                Sesion.setUsuarioActual(dao.loadLogin(tfUsuario.getText(), tfPass.getText() ));

                App.loadFXML("pedidos-view.fxml", "Pedidos de " + Sesion.getUsuarioActual().getNombreusuario());
            } else {
                tfUsuario.setText("");
                tfPass.setText("");
                info.setText("Nombre de usuario o contraseña incorrecto(s)");
            }

        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

}