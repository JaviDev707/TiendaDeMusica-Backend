package com.proyectos.tiendaDeMusica.DTO;

public record ReseñaDTO(
    Long id,
    String emailUsuario,
    Long idProducto,
    Integer puntuacion,
    String comentario
) {}
