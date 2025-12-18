package com.proyectos.tiendaDeMusica.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DiscoDTO extends ProductoDTO {
    
    private String genero; 
    private String artista;
    private Integer year;

}
