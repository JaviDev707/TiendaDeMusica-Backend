package com.proyectos.tiendaDeMusica.Service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.tiendaDeMusica.Entity.Carrito;
import com.proyectos.tiendaDeMusica.Entity.ItemCarrito;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Exception.ApiException;
import com.proyectos.tiendaDeMusica.Repository.CarritoRepository;
import com.proyectos.tiendaDeMusica.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoService productoService;

    public Carrito obtenerCarrito(Long usuarioId) {

        if (carritoRepository.findByUsuarioId(usuarioId).isEmpty()) {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new ApiException("Usuario no encontrado con ID: " + usuarioId, HttpStatus.NOT_FOUND)));
            return carritoRepository.save(nuevoCarrito);
        }
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ApiException(
                        "Carrito no encontrado para el usuario con ID: " + usuarioId, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Carrito agregarItem(Long usuarioId, Long productoId, int cantidad) {
        if (cantidad <= 0) {
            throw new ApiException("La cantidad debe ser mayor que cero.", HttpStatus.BAD_REQUEST);
        }

        Carrito carrito = obtenerCarrito(usuarioId);

        Producto producto = productoService.obtenerProducto(productoId); 

        // Busco si el producto ya esta en el carrito
        Optional<ItemCarrito> itemExistenteOpt = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst();

        if (itemExistenteOpt.isPresent()) {
            ItemCarrito item = itemExistenteOpt.get();
            item.setCantidad(item.getCantidad() + cantidad);

        } else {
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(cantidad);

            carrito.getItems().add(nuevoItem);
        }

        return carritoRepository.save(carrito);
    }

    @Transactional
    public Carrito quitarItem(Long usuarioId, Long productoId) {
        Carrito carrito = obtenerCarrito(usuarioId);

        // Busco el ItemCarrito por id
        ItemCarrito itemARemover = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new ApiException("Ítem no encontrado en el carrito.", HttpStatus.NOT_FOUND));

        carrito.getItems().remove(itemARemover);

        return carritoRepository.save(carrito);
    }

    @Transactional
    public Carrito actualizarCantidad(Long usuarioId, Long productoId, int nuevaCantidad) {

        if (nuevaCantidad <= 0) {
            throw new ApiException("La cantidad debe ser mayor que cero.", HttpStatus.BAD_REQUEST);
        }

        Carrito carrito = obtenerCarrito(usuarioId);

        ItemCarrito itemAActualizar = carrito.getItems().stream()
                .filter(item -> item.getProducto().getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new ApiException("Ítem no encontrado en el carrito.", HttpStatus.NOT_FOUND));

        itemAActualizar.setCantidad(nuevaCantidad);

        return carritoRepository.save(carrito);
    }

    @Transactional
    public void eliminarCarrito(Long carritoId) {

        if (!carritoRepository.existsById(carritoId)) {
            throw new ApiException("Carrito no encontrado con ID: " + carritoId, HttpStatus.NOT_FOUND);
        }

        carritoRepository.deleteById(carritoId);
    }
}
