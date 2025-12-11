package com.proyectos.tiendaDeMusica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Reseña;

@Repository
public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
    
    List<Reseña> findByProductoId(Long idProducto);
    List<Reseña> findByUsuarioId(Long idUsuario);

}
