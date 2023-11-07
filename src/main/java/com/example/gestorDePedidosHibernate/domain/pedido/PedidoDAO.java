package com.example.gestorDePedidosHibernate.domain.pedido;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.HibernateUtils;
//import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements DAO<Pedido> {
    @Override
    public ArrayList<Pedido> getAll() {
        return null;
    }

    @Override
    public Pedido get(Long id) {
        return null;
    }

    @Override
    public Pedido save(Pedido data) {
        return null;
    }

    @Override
    public void update(Pedido data) {

    }

    @Override
    public void delete(Pedido data) {

    }

    public List<Pedido> pedidosDeUnUsuario(Usuario usuario) {

        List<Pedido> salida = new ArrayList<>();
        try(Session s = HibernateUtils.getSessionFactory().openSession()){
            Query<Usuario> q = s.createQuery("from Usuario where id_usuario =: id",Usuario.class);
            q.setParameter("id",usuario.getId_usuario());
            salida = q.getSingleResult().getPedidos();
        }
        for (Pedido pedido: salida){
            Double total=0.0;
            for (Item item: pedido.getItems()){
                total = total + item.getCantidad()*item.getProducto().getPrecio();
            }
            DecimalFormat formato = new DecimalFormat("#.00");
            pedido.setTotal(formato.format(total));
        }
        return salida;
    }

    public List<Producto> detallesDeUnPedido(Pedido pedidoPulsado) {
        List<Producto> result = new ArrayList<>();
        try(Session s = HibernateUtils.getSessionFactory().openSession()){
            Query<Pedido> q = s.createQuery("from Pedido where id_pedido =: id",Pedido.class);
            q.setParameter("id",pedidoPulsado.getId_pedido());
            for (Item item: q.getSingleResult().getItems()){

                result.add(item.getProducto());
            }
        }
        return  result;
//        List<Pedido> salida = new ArrayList<Pedido>();
//        try(Session s = HibernateUtils.getSessionFactory().openSession()){
//            Query<Usuario> q = s.createQuery("from Usuario where id_usuario =: id",Usuario.class);
//            q.setParameter("id",usuario.getId_usuario());
//            salida = q.getSingleResult().getPedidos();
//        }
//        return salida;
    }

//    public Double detallesDeUnPedido(Pedido pedido) {
//        Double suma = null;
//        try(Session session = HibernateUtils.getSessionFactory().openSession()){
//            Query<Producto> q = session.createQuery(
//                    "SELECT new com.example.gestorDePedidosHibernate.domain.producto.Producto(producto.id_producto,producto.nombre,producto.precio,items.cantidad)" +
//                            "FROM Producto producto " +
//                            "JOIN producto.items items " +
//                            "JOIN items.pedido pedido " +
//                            "WHERE pedido.usuario_id = :id");
////                    "FROM Pedido pedido " +
////                    "JOIN pedido.items items " +
////                    "JOIN items.producto producto " +
////                    "WHERE pedido.usuario_id = :id");
//            q.setParameter("id",pedido.getId_pedido());
//            try {
//                suma =  q.getResultList();
//            }catch (Exception ignored){
//            }
//        }
//        return suma;
//    }
}
