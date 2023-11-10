package com.example.gestorDePedidosHibernate.domain;

import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Sesion {

    @Setter
    @Getter
    private static Usuario usuarioActual;

    @Setter
    @Getter
    private static Pedido pedidoPulsado;

    @Setter
    @Getter
    private static Item itemPulsado;




}