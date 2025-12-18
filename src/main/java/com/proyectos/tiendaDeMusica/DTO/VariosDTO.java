package com.proyectos.tiendaDeMusica.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class VariosDTO extends ProductoDTO{
    
    private String tipo;
    private String marca;

}
