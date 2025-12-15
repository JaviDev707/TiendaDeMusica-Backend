package com.proyectos.tiendaDeMusica.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.tiendaDeMusica.DTO.ReseñaDTO;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Entity.Reseña;
import com.proyectos.tiendaDeMusica.Entity.Usuario;
import com.proyectos.tiendaDeMusica.Repository.ProductoRepository;
import com.proyectos.tiendaDeMusica.Repository.ReseñaRepository;
import com.proyectos.tiendaDeMusica.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReseñaService {

    private final ReseñaRepository reseñaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Reseña crearReseña(ReseñaDTO reseñaDTO, Long idUsuario) {

        Reseña reseña = new Reseña();

        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        Producto producto = productoRepository.findById(reseñaDTO.idProducto())
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Reseña unica
        if (reseñaRepository.existsByUsuarioAndProducto(usuario, producto)) {
            throw new IllegalArgumentException("Ya has dejado una reseña para este producto.");
        }

        int puntuacion = reseñaDTO.puntuacion();
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }

        reseña.setUsuario(usuario);
        reseña.setProducto(producto);
        reseña.setPuntuacion(puntuacion);
        reseña.setComentario(reseñaDTO.comentario());
        reseña.setFecha(LocalDateTime.now());

        return reseñaRepository.save(reseña);
    }

    @Transactional(readOnly = true)
    public List<Reseña> obtenerReseñaPorProducto(Long idProducto) {
        return reseñaRepository.findByProductoId(idProducto);
    }

    @Transactional(readOnly = true)
    public List<Reseña> obtenerReseñaPorUsuario(Long idUsuario) {
        return reseñaRepository.findByUsuarioId(idUsuario);
    }

}
