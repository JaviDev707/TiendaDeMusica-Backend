package com.proyectos.tiendaDeMusica.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.tiendaDeMusica.DTO.ReseñaDTO;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Entity.Reseña;
import com.proyectos.tiendaDeMusica.Entity.Usuario;
import com.proyectos.tiendaDeMusica.Exception.ApiException;
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
            .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        Producto producto = productoRepository.findById(reseñaDTO.idProducto())
            .orElseThrow(() -> new ApiException("Producto no encontrado", HttpStatus.NOT_FOUND));

        // Reseña unica
        if (reseñaRepository.existsByUsuarioAndProducto(usuario, producto)) {
            throw new ApiException("Ya has dejado una reseña para este producto.", HttpStatus.BAD_REQUEST);
        }

        int puntuacion = reseñaDTO.puntuacion();
        if (puntuacion < 1 || puntuacion > 5) {
            throw new ApiException("La puntuación debe estar entre 1 y 5.", HttpStatus.BAD_REQUEST);
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
