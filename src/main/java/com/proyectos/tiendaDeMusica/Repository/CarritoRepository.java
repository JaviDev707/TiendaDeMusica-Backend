package com.proyectos.tiendaDeMusica.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    
    Optional<Carrito> findByUsuarioId(Long idUsuario);

}
