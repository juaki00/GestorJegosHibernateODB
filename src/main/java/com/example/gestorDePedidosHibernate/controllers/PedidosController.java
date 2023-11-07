package com.example.gestorDePedidosHibernate.controllers;
import com.example.gestorDePedidosHibernate.App;
import com.example.gestorDePedidosHibernate.domain.Sesion;
import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;

import com.example.gestorDePedidosHibernate.domain.pedido.PedidoDAO;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PedidosController implements Initializable {
    @javafx.fxml.FXML
    private Label labelNombre;
    @javafx.fxml.FXML
    private TableView<Pedido> tablaPedidos;

    @javafx.fxml.FXML
    private TableColumn<Pedido,String> cFecha;
    @javafx.fxml.FXML
    private TableColumn<Pedido,String> cUsuario;
    @javafx.fxml.FXML
    private TableColumn<Pedido,String> cTotal;
    @javafx.fxml.FXML
    private TableColumn<Pedido,String>  cId;
    @javafx.fxml.FXML
    private Button btnLogout;
    @javafx.fxml.FXML
    private BorderPane ventana;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private Button btnDetalles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            // Listener
//        tablaPedidos.getSelectionModel().selectedItemProperty().addListener((observableValue, vOld, vNew) -> {
//            Sesion.setPedidoPulsado(vNew);
//            App.loadFXML("detallesPedido-view.fxml", "Detalles del pedido");
//        });

        Usuario usuario = Sesion.getUsuarioActual();
        PedidoDAO daoPedido = new PedidoDAO();
        List<Pedido> pedidosDeUser = daoPedido.pedidosDeUnUsuario(usuario) ;

            //Cambiar Titulo
        labelNombre.setText("Pedidos de "+ usuario.getNombreusuario()+" ("+ usuario.getEmail()+")");
            //Rellenar la tabla
        cId.setCellValueFactory( (fila) -> {
            Long id = fila.getValue().getId_pedido();
            return new SimpleStringProperty(id.toString());
        });
        cFecha.setCellValueFactory( (fila) ->{
            String cantidad = fila.getValue().getFecha();
            return new SimpleStringProperty(cantidad);
        });
        cUsuario.setCellValueFactory( (fila) ->{
            String nombre = Sesion.getUsuarioActual().getNombreusuario();
            return new SimpleStringProperty(nombre);
        });
        cTotal.setCellValueFactory( (fila) -> {
            String total = fila.getValue().getTotal();
            return new SimpleStringProperty(total);
        });
        tablaPedidos.getItems().addAll(pedidosDeUser);

        }

    @javafx.fxml.FXML
    public void logoutButton() {
        Sesion.setUsuarioActual(null);
        Sesion.setPedidoPulsado(null);
        App.loadFXML("login-view.fxml", "Iniciar Sesión");
    }


    @javafx.fxml.FXML
    public void eliminar(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void detalles(ActionEvent actionEvent) {
        Sesion.setPedidoPulsado(tablaPedidos.getSelectionModel().selectedItemProperty().get());
        App.loadFXML("detallesPedido-view.fxml", "Detalles del pedido");
    }
}
