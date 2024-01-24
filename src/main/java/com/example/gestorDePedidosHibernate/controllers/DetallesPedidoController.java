package com.example.gestorDePedidosHibernate.controllers;

import com.example.gestorDePedidosHibernate.App;
import com.example.gestorDePedidosHibernate.domain.HibernateUtils;
import com.example.gestorDePedidosHibernate.domain.Sesion;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.pedido.PedidoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URL;
import java.util.*;

/**
 * Controlador de la vista Detalles de un pedido
 */
public class DetallesPedidoController implements Initializable
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
    private Label log;

    /**
     * Metodo inicializar
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       PedidoDAO dao = new PedidoDAO();
        List<Item> items = dao.detallesDeUnPedido(Sesion.getPedidoPulsado());

             //Cambiar titulo
        labelTitulo.setText("Pedido número " + Sesion.getPedidoPulsado().getId_pedido());

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

    /**
     * Función botón atrás
     */
    @FXML
    public void atras() {
        Sesion.setPedidoPulsado( null );
        App.loadFXML("pedidos-view.fxml", "Pedidos de " + Sesion.getUsuarioActual().getNombreusuario());
    }

    /**
     * Función botón logout
     */
    @FXML
    public void logout() {
        Sesion.logout();
        App.loadFXML("login-view.fxml", "Iniciar Sesión");
    }

    @FXML
    public void exportarPdf( ){
        HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();

// Obtener la conexión utilizando session.doWork()
        HibernateUtils.getSessionFactory().getCurrentSession().doWork(c -> {

            HashMap<String,Object> hm = new HashMap<>();
            hm.put( "idPedido", Sesion.getPedidoPulsado().getId_pedido() );
            JasperPrint jasperPrint;
            try {
                jasperPrint = JasperFillManager.fillReport( "Leaf_Green.jasper", hm, c);
                JRPdfExporter exp = new JRPdfExporter();
                exp.setExporterInput(new SimpleExporterInput( jasperPrint));
                exp.setExporterOutput(new SimpleOutputStreamExporterOutput( "pedidos.pdf"));
                exp.setConfiguration(new SimplePdfExporterConfiguration());
                exp.exportReport();
                log.setText( "Pdf exportado corectamente" );
                log.setStyle( "-fx-text-fill: #48ff00" );
            } catch ( JRException e ) {
                log.setText( "Error al exportar el pdf" );
                log.setStyle( "-fx-text-fill: red" );
            }
        });
        HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    @FXML
    public void reporteNuevaVentana() {
        HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();

// Obtener la conexión utilizando session.doWork()
        HibernateUtils.getSessionFactory().getCurrentSession().doWork(c -> {

            HashMap<String,Object> hm = new HashMap<>();
            hm.put( "idPedido", Sesion.getPedidoPulsado().getId_pedido() );
            JasperPrint jasperPrint;
            try {
                jasperPrint = JasperFillManager.fillReport( "Leaf_Green.jasper", hm, c);
                JRViewer viewer = new JRViewer( jasperPrint);

                JFrame frame = new JFrame( "Listado de Juegos");
                frame.getContentPane().add(viewer);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            } catch ( JRException e ) {
                log.setText( "Error al crear el reporte" );
                log.setStyle( "-fx-text-fill: red" );
            }
        });
        HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
    }
}