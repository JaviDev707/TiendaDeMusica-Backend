package com.proyectos.tiendaDeMusica.Entity;

import java.math.BigDecimal;

import com.proyectos.tiendaDeMusica.Enums.TipoProducto;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos") 
@Inheritance(strategy = InheritanceType.JOINED) // Herencia
@DiscriminatorColumn(name = "tipo_producto", discriminatorType = DiscriminatorType.STRING) 
@Data
@NoArgsConstructor
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto" , insertable = false, updatable = false)
    private TipoProducto tipoProducto;

    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private String imageUrl;
    @Column(length = 1000)
    private String descripcion;

}
