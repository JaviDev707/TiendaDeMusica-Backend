package com.proyectos.tiendaDeMusica.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "discos") 
@PrimaryKeyJoinColumn(name = "id") // ID de la tabla productos como FK y PK
@DiscriminatorValue("DISCO") // Valor para la columna 'tipo_producto' en la tabla base
@Data
@EqualsAndHashCode(callSuper = true) //Herencia con Lombok
public class Disco extends Producto {
    
    private String genero; 
    private String artista;
    private String nombre;
    private Integer year;
    
}
