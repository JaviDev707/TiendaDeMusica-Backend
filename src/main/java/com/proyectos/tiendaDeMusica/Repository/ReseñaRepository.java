package com.proyectos.tiendaDeMusica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Reseña;

@Repository
public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
    
}
