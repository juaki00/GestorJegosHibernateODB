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

    public List<Item> detallesDeUnPedido(Pedido pedidoPulsado) {
        List<Item> result;
        try(Session s = HibernateUtils.getSessionFactory().openSession()){
            Query<Pedido> q = s.createQuery("from Pedido where id_pedido =: id",Pedido.class);
            q.setParameter("id",pedidoPulsado.getId_pedido());
            result = new ArrayList<>(q.getSingleResult().getItems());
        }
        return  result;
    }

    public String calculaTotalDeUnPedido(Pedido pedido){
        Double sumaTotal = 0.0;
        List<Item> items= new ArrayList<Item>();
        for (Item i: pedido.getItems()){
            sumaTotal = sumaTotal + (i.getCantidad()*i.getProducto().getPrecio());
        }
        return sumaTotal.toString();
    }
}
