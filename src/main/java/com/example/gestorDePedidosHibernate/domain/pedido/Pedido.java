package com.example.gestorDePedidosHibernate.domain.pedido;

import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;
//    private String codigo;
    private String fecha;
    private String total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Item> items;


    @Override
    public String toString() {
        return "Pedido{" +
                "id_pedido=" + id_pedido +
//                ", codigo='" + codigo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuario=" + usuario.getNombreusuario() +
                ", total='" + total + '\'' +
                ", items=" + items +
                '}';
    }
}
