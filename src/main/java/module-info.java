module com.example.gestorjegoshibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql; //para fechas

    opens com.example.gestorjegoshibernate.domain.usuario;
    opens com.example.gestorjegoshibernate to javafx.fxml;
    exports com.example.gestorjegoshibernate;
    exports com.example.gestorjegoshibernate.controllers;
    opens com.example.gestorjegoshibernate.controllers to javafx.fxml;
}