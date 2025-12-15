package com.proyectos.tiendaDeMusica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Entity.Reseña;
import com.proyectos.tiendaDeMusica.Entity.Usuario;

@Repository
public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
    
    List<Reseña> findByProductoId(Long idProducto);
    List<Reseña> findByUsuarioId(Long idUsuario);

    boolean existsByUsuarioAndProducto(Usuario usuario, Producto producto);

}
