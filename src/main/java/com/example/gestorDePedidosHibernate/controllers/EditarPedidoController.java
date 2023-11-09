package com.example.gestorDePedidosHibernate.controllers;

import com.example.gestorDePedidosHibernate.App;
import com.example.gestorDePedidosHibernate.domain.Sesion;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.pedido.PedidoDAO;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditarPedidoController implements Initializable
{

    @FXML
    private TableView<Item> tablaDetallesPedido;
    @FXML
    private TableColumn<Item,String>  cNombre;
    @FXML
    private TableColumn<Item,String>  cPrecio;
    @FXML
    private TableColumn<Item,String>  cCantidad;
    @FXML
    private Label labelTitulo;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnAtras;
    @FXML
    private VBox menuLateral;
    @FXML
    private TextField textNombre;
    @FXML
    private Spinner<Integer> spinnerCantidad;
    @FXML
    private Slider sliderPrecio;
    @FXML
    private TextField labelPrecio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //listener del slider
        sliderPrecio.valueProperty().addListener((observableValue, number, t1) -> {
            labelPrecio.setText(t1.intValue()+"");
        });
        //listener de la tabla
        tablaDetallesPedido.getSelectionModel().selectedItemProperty().addListener((observableValue, producto, t1) -> {
            menuLateral.setDisable(false);

            textNombre.setText(t1.getProducto().getNombre());
            sliderPrecio.setValue(t1.getProducto().getPrecio());
            labelPrecio.setText(Math.round(sliderPrecio.getValue())+"");
            spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,t1.getCantidad(),1));
        });

       PedidoDAO dao = new PedidoDAO();
        List<Item> items = dao.detallesDeUnPedido(Sesion.getPedidoPulsado());

             //Cambiar titulo
        labelTitulo.setText("Editar pedido " + Sesion.getPedidoPulsado().getId_pedido());

            //Rellenar la tabla
        cNombre. setCellValueFactory( (fila) -> {
            String nombre = fila.getValue().getProducto().getNombre();
            return new SimpleStringProperty(nombre);
        });
        cCantidad. setCellValueFactory( (fila) -> {
            int cantidad = fila.getValue().getCantidad();
            return new SimpleStringProperty(Integer.toString(cantidad));
        });
        cPrecio. setCellValueFactory( (fila) -> {
            double precio = fila.getValue().getProducto().getPrecio();
            return new SimpleStringProperty(Double.toString(precio));
        });
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        observableList.addAll(items);
        tablaDetallesPedido.setItems(observableList);
}

    @FXML
    public void atras() {
        App.loadFXML("pedidos-view.fxml", "Pedidos de " + Sesion.getUsuarioActual().getNombreusuario());
    }

    @FXML
    public void logout() {
        Sesion.setUsuarioActual(null);
        Sesion.setPedidoPulsado(null);
        App.loadFXML("login-view.fxml", "Iniciar Sesión");
    }
}