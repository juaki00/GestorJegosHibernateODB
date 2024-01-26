package com.example.gestorDePedidosHibernate.domain;

import lombok.extern.java.Log;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Connection;

/**
 * Utilidades de hibernate
 */
@Log
public class ODB {
    private static final EntityManagerFactory emf;

    static{
        emf = Persistence.createEntityManagerFactory("data.odb");
    }

//    public static void buildSessionFactory( ) {
//        try {
//            Configuration cfg = new Configuration();
//            cfg.configure();
//            sf = cfg.buildSessionFactory();
//            log.info("SessionFactory creada con exito!");
//        } catch(Exception e){
//            log.severe("Error al crear SessionFactory");
//        }
//    }

    /**
     * Sesion Factory
     * @return devuelve una instancia de una sesion factory de hibernate
     */
    public static EntityManagerFactory getEntityManagerFactory(){

        return emf;
    }

    public static Connection getConnection(){

        return emf.unwrap( Connection.class );
    }
}
