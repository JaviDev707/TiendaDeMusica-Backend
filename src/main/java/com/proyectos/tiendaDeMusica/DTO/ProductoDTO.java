package com.proyectos.tiendaDeMusica.DTO;

import java.math.BigDecimal;

import com.proyectos.tiendaDeMusica.Enums.TipoProducto;

public record ProductoDTO(
    Long id,
    String nombre,
    String descripcion,
    BigDecimal precio,
    int stock,
    TipoProducto tipoProducto
) {}
