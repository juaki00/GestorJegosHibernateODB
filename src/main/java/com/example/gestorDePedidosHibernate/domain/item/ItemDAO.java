package com.example.gestorDePedidosHibernate.domain.item;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.HibernateUtils;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ItemDAO implements DAO<Item> {
    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public Item get(Long id) {
        return null;
    }

    @Override
    public Item save(Item data) {
        return null;
    }

    @Override
    public void update(Item data) {

        try(Session s = HibernateUtils.getSessionFactory().openSession()){
            Transaction t = s.beginTransaction();
            Item nuevoItem = s.get(Item.class, data.getId_item());
            Producto nuevoProducto = s.get(Producto.class, data.getProducto().getId_producto());
            nuevoProducto.setNombre(data.getProducto().getNombre());
            nuevoProducto.setPrecio(data.getProducto().getPrecio());
            nuevoItem.setCantidad(data.getCantidad());
            nuevoItem.setProducto(nuevoProducto);
            nuevoItem.setPedido(data.getPedido());
            t.commit();
        }
    }

    @Override
    public void delete(Item data) {

    }
}
