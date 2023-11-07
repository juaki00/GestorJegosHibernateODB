package com.example.gestorjegoshibernate.domain.usuario;

import com.example.gestorjegoshibernate.domain.juego.Game;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario")
    private String username;
    @Column(name = "contraseña")
    private String password;

    @Transient //no se almacena a la base de datos
    private Long gamesQuantity;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER) //Para rellenar todos los atributos relacionados con otras tablas
    private List<Game> games = new ArrayList<Game>(0);


    public Long getGamesQuantity() {
        gamesQuantity = (long) games.size();
        return gamesQuantity;
    }
    public void addGame(Game g){
        g.setUser(this);
        games.add(g);
    }

    public void removeGame(Game g){
        games.remove(g);
//        g.setUser(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
