package com.proyectos.tiendaDeMusica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Disco;

@Repository
public interface DiscoRepository extends JpaRepository<Disco, Long> {
    
}
