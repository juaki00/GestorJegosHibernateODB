package com.example.gestorDePedidosHibernate.domain.usuario;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.HibernateUtils;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import lombok.extern.java.Log;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

@Log
public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public List<Usuario> getAll() {
        List<Usuario> result = null;
        try(Session s = HibernateUtils.getSessionFactory().openSession()){
            Query<Usuario> q = s.createQuery("from Usuario",Usuario.class);
            result = q.getResultList();
        }
        return  result;
    }

    @Override
    public Usuario get(Long id) {
        return null;
    }

    @Override
    public Usuario save(Usuario data) {
        return null;
    }

    @Override
    public void update(Usuario data) {

    }

    @Override
    public void delete(Usuario data) {

    }

    public boolean isCorrectUser(String user, String pass) {
        return loadLogin(user,pass) != null;
    }

    public Usuario loadLogin(String user, String pass) {
        Usuario result = null;

        if(HibernateUtils.getSessionFactory()==null){
            HibernateUtils.buildSessionFactory();
        }

        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Query<Usuario> q = session.createQuery("from Usuario where nombreusuario=:u and pass=:p", Usuario.class);
            q.setParameter("u",user);
            q.setParameter("p",pass);

            result = q.getSingleResult();
        }
        catch (Exception e){
            log.severe( "Error al iniciar la sesion" );
        }
        return result;
    }

}

