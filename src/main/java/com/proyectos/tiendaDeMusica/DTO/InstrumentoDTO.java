package com.proyectos.tiendaDeMusica.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class InstrumentoDTO extends ProductoDTO{
    
    private String tipo; 
    private String marca;
    private String modelo;

}
