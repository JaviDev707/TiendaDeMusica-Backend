package com.proyectos.tiendaDeMusica.DTO;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.math.BigDecimal;
import lombok.Data; 

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,              
    include = JsonTypeInfo.As.PROPERTY,      
    property = "tipoProducto" // Campo discriminador en el JSON
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DiscoDTO.class, name = "DISCO"),
    @JsonSubTypes.Type(value = InstrumentoDTO.class, name = "INSTRUMENTO"),
    @JsonSubTypes.Type(value = VariosDTO.class, name = "VARIOS")
})
@Data
public abstract class ProductoDTO {
    
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private int stock;
    private String imageUrl;
    private String descripcion;
    // Tipo producto se maneja mediante JsonTypeInfo y JsonSubTypes
}