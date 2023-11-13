package com.example.gestorDePedidosHibernate.domain.pedido;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.HibernateUtils;
//import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Log
public class PedidoDAO implements DAO<Pedido> {
    @Override
    public ArrayList<Pedido> getAll( ) {
        return null;
    }

    @Override
    public Pedido get( Long id ) {
        return null;
    }

    @Override
    public Pedido save( Pedido data ) {

        Pedido salida = null;
        try ( org.hibernate.Session s = HibernateUtils.getSessionFactory( ).openSession( ) ) {
            Transaction t = s.beginTransaction( );
            s.persist( data );
            t.commit( );
            salida = data;
        } catch ( Exception e ) {
            log.severe( "Error al guardar. " + data.toString( ) );
        }
        return salida;
    }

    @Override
    public void update( Pedido data ) {

    }

    @Override
    public void delete( Pedido data ) {

        HibernateUtils.getSessionFactory().inTransaction(s -> {
            Query<Item> q = s.createQuery( "from Item where pedido =: ped" , Item.class );
            q.setParameter( "ped" , data );
            List<Item> items = q.getResultList( );
            Pedido i = s.get( Pedido.class , data.getId_pedido( ) );
            items.forEach( it -> s.remove( it ) );

            s.remove( i );
        });
    }

    public List<Pedido> pedidosDeUnUsuario( Usuario usuario ) {

        List<Pedido> salida = new ArrayList<>( );
        try ( Session s = HibernateUtils.getSessionFactory( ).openSession( ) ) {
            Query<Usuario> q = s.createQuery( "from Usuario where id_usuario =: id" , Usuario.class );
            q.setParameter( "id" , usuario.getId_usuario( ) );
            salida = q.getSingleResult( ).getPedidos( );
        }
        for (Pedido pedido : salida) {
            Double total = 0.0;
            for (Item item : pedido.getItems( )) {
                total = total + item.getCantidad( ) * item.getProducto( ).getPrecio( );
            }
            DecimalFormat formato = new DecimalFormat( "#.00" );
            pedido.setTotal( formato.format( total ) );
        }
        return salida;
    }

    public List<Item> detallesDeUnPedido( Pedido pedidoPulsado ) {
        List<Item> result;
        try ( Session s = HibernateUtils.getSessionFactory( ).openSession( ) ) {
            Query<Pedido> q = s.createQuery( "from Pedido where id_pedido =: id" , Pedido.class );
            q.setParameter( "id" , pedidoPulsado.getId_pedido( ) );
            result = new ArrayList<>( q.getSingleResult( ).getItems( ) );
        }
        return result;
    }

    public List<String> todosLosProductos( ) {
        List<String> resultado = new ArrayList<String>( );
        try ( Session s = HibernateUtils.getSessionFactory( ).openSession( ) ) {
            Query<String> q = s.createQuery( "select distinct p.nombre from Producto p" , String.class );
            resultado = q.getResultList( );
        }

        return resultado;
    }

    public void insertarItemAPedido( Pedido ped , Integer cant , Producto prod ) {
        try ( org.hibernate.Session s = HibernateUtils.getSessionFactory( ).openSession( ) ) {
            Transaction t = s.beginTransaction( );
            //Crear u nuevo item
            Item item = new Item( );
            item.setCantidad( cant );
            item.setPedido( ped );
            item.setProducto( prod );
            s.persist( item );
            t.commit( );
        } catch ( Exception e ) {
            log.severe( "Error al insertar un nuevo item" );
        }
    }

    public Producto buscaProductoEnPedido( String nombreProducto , Pedido pedido ) {
        Producto producto = null;
        try ( org.hibernate.Session s = HibernateUtils.getSessionFactory( ).openSession( ) ) {
            Query<Producto> q = s.createQuery(
                    "select i.producto from Item i where i.producto.nombre =: nombre and i.pedido.id_pedido =: idPedido" , Producto.class );
            q.setParameter( "nombre" , nombreProducto );
            q.setParameter( "idPedido" , pedido.getId_pedido( ) );
            producto = q.getSingleResultOrNull( );
        }
        return producto;
    }

    public boolean estaProductoEnPedido( String nombreProducto , Pedido pedido ) {
        return buscaProductoEnPedido( nombreProducto , pedido ) != null;
    }

    public String calculaTotalDeUnPedido( Pedido pedido ) {
        Double sumaTotal = 0.0;
        List<Item> items = new ArrayList<Item>( );
        for (Item i : pedido.getItems( )) {
            sumaTotal = sumaTotal + (i.getCantidad( ) * i.getProducto( ).getPrecio( ));
        }
        return sumaTotal.toString( );
    }
}
