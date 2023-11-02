package com.example.gestorjegoshibernate.domain.juego;

import com.example.gestorjegoshibernate.domain.DAO;

import java.util.ArrayList;

import com.example.gestorjegoshibernate.domain.HibernateUtils;
import com.example.gestorjegoshibernate.domain.usuario.User;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class GameDAO implements DAO<Game> {

    @Override
    public ArrayList<Game> getAll() {
        return null;
    }

    public ArrayList<Game> getAllFromUser(User u) {
        ArrayList<Game> results = new ArrayList<Game>(0);
        try(Session s = HibernateUtils.getSessionFactory().openSession()){
            Query<Game> q = s.createQuery("from Game where usuarioId=:userId",Game.class);
            q.setParameter("userId",u.getId());
            results = (ArrayList<Game>) q.getResultList();
        }
        return results;
    }

    @Override
    public Game get(Long id) {
        return null;
    }

    @Override
    public Game save(Game data) {
        return null;
    }

    @Override
    public void update(Game data) {

    }

    @Override
    public void delete(Game data) {

    }
}
