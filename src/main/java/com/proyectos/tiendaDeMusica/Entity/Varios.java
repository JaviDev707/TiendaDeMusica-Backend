package com.proyectos.tiendaDeMusica.Entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "varios") 
@PrimaryKeyJoinColumn(name = "id") // ID de la tabla productos como FK y PK
@DiscriminatorValue("VARIOS") // Valor para la columna 'tipo_producto' en la tabla base
@Data
@EqualsAndHashCode(callSuper = true) //Herencia con Lombok
public class Varios extends Producto {
    
    private String tipo; 
    private String nombre;
    private String marca;
    
}
