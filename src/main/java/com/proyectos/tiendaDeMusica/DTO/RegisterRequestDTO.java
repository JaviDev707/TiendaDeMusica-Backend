package com.proyectos.tiendaDeMusica.DTO;

public record RegisterRequestDTO(
    String email,
    String password,
    String nombre,
    String apellido,
    String codigoPostal,
    String localidad,
    String direccion
) {}
