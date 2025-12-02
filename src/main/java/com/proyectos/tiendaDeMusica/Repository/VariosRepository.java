package com.proyectos.tiendaDeMusica.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectos.tiendaDeMusica.Entity.Varios;

@Repository
public interface VariosRepository extends JpaRepository<Varios, Long> {
    
}
