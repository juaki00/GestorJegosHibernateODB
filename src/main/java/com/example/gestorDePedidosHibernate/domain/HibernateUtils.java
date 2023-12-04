package com.example.gestorDePedidosHibernate.domain;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.SQLException;

/**
 * Utilidades de hibernate
 */
@Log
public class HibernateUtils {
    private static SessionFactory sf;

    static {
        buildSessionFactory( );
    }

    public static void buildSessionFactory( ) {
        try {
            Configuration cfg = new Configuration();
            cfg.configure();
            sf = cfg.buildSessionFactory();
            log.info("SessionFactory creada con exito!");
        } catch(Exception e){
            log.severe("Error al crear SessionFactory");
        }
    }

    /**
     * Sesion Factory
     * @return devuelve una instancia de una sesion factory de hibernate
     */
    public static SessionFactory getSessionFactory(){

        return sf;
    }
}
