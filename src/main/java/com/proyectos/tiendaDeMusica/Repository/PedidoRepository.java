package com.proyectos.tiendaDeMusica.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectos.tiendaDeMusica.Entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
}
