package com.proyectos.tiendaDeMusica.DTO;

public record ReseñaDTO(
    Long id,
    Long idProducto,
    Integer puntuacion,
    String comentario
) {}
