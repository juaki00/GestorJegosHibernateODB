package com.example.gestorjegoshibernate.domain.usuario;

import com.example.gestorjegoshibernate.domain.DAO;
import com.example.gestorjegoshibernate.domain.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UserDAO implements DAO<User> {
    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User save(User data) {
        return null;
    }

    @Override
    public void update(User data) {

    }

    @Override
    public void delete(User data) {

    }

    public User validateUser(String name, String pass){
        User result = null;

        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Query<User> q = session.createQuery("from User where username=:u and password=:p",User.class);
            q.setParameter("u",name);
            q.setParameter("p",pass);

            try {
                result = q.getSingleResult();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return result;
//        --------Igual pero con lambda--------
//        HibernateUtils.getSessionFactory().inSession( (session -> {
//            Query<User> q = session.createQuery("from User where username=:u and password=:p",User.class);
//            q.setParameter("u",name);
//            q.setParameter("p",pass);
//
//            try {
//                result = q.getSingleResult();
//            }catch (Exception ignored){
//                throw new RuntimeException();
//            }
//        }));
    }
}
