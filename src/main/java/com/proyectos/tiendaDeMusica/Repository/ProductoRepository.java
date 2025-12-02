package com.proyectos.tiendaDeMusica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
