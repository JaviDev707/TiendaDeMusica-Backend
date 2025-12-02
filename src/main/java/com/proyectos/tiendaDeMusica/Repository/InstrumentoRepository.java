package com.proyectos.tiendaDeMusica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Instrumento;

@Repository
public interface InstrumentoRepository extends JpaRepository<Instrumento, Long> {

    
} 