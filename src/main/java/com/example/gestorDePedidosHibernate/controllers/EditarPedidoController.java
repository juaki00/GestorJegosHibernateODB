package com.example.gestorDePedidosHibernate.controllers;

import com.example.gestorDePedidosHibernate.App;
import com.example.gestorDePedidosHibernate.domain.Sesion;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.item.ItemDAO;
import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;
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
    private static PedidoDAO pedidoDAO;
    private static ItemDAO itemDAO;

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
    @FXML
    private Button btnEditar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pedidoDAO = new PedidoDAO();
        itemDAO = new ItemDAO();
        //listener del sliderPrecio
        sliderPrecio.valueProperty().addListener((observableValue, number, t1) -> {
            labelPrecio.setText(t1.doubleValue()+"");
        });
        //Listener del labelPrecio
        labelPrecio.textProperty().addListener((observableValue, eventHandler, t1) -> {
            try {
                sliderPrecio.setValue(Double.parseDouble(labelPrecio.getText()));
            }
            catch (Exception e){

            }
        });

        //listener de la tabla
        tablaDetallesPedido.getSelectionModel().selectedItemProperty().addListener((observableValue, producto, t1) -> {

            menuLateral.setDisable(false);

            if(t1!=null) Sesion.setItemPulsado(t1);
            textNombre.setText(Sesion.getItemPulsado().getProducto().getNombre());
            sliderPrecio.setValue(Sesion.getItemPulsado().getProducto().getPrecio());
            labelPrecio.setText(Math.round(sliderPrecio.getValue())+"");
            spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,Sesion.getItemPulsado().getCantidad(),1));
        });

             //Cambiar titulo
        labelTitulo.setText("Editar pedido " + Sesion.getPedidoPulsado().getId_pedido());

            //Rellenar la tabla
        rellenarTabla();
    }

    private void rellenarTabla() {
        List<Item> items = pedidoDAO.detallesDeUnPedido(Sesion.getPedidoPulsado());
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

    @FXML
    public void editar() {
        Item itemModificado = Sesion.getItemPulsado();
        Producto productoModificado = Sesion.getItemPulsado().getProducto();
        productoModificado.setPrecio(Double.valueOf(labelPrecio.getText()));
        productoModificado.setNombre(textNombre.getText());
        itemModificado.setCantidad(spinnerCantidad.getValue());
        itemModificado.setProducto(productoModificado);
        itemDAO.update(itemModificado);
        this.rellenarTabla();
        System.out.println(Sesion.getItemPulsado().getCantidad());
    }
}
