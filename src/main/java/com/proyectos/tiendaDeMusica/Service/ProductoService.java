package com.proyectos.tiendaDeMusica.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectos.tiendaDeMusica.DTO.ProductoDTO;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> listarTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Producto actualizarStock(Long id, int cantidadVendida) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));

        if (producto.getStock() < cantidadVendida) {
            throw new IllegalArgumentException("Stock insuficiente para el producto con ID: " + id);
        }

        int nuevoStock = producto.getStock() - cantidadVendida;
        producto.setStock(nuevoStock);

        return productoRepository.save(producto);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto a eliminar no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Transactional
    public Producto crearProducto(ProductoDTO productoRequest) {
        return productoRepository.save(mapProducto(productoRequest));
    }
    
    @Transactional
    public Producto actualizarProducto(ProductoDTO productoDTO) {
        if (!productoRepository.existsById(productoDTO.id())) {
            throw new IllegalArgumentException("Producto a actualizar no encontrado con ID: " +
                    productoDTO.id());
        }
        return productoRepository.save(mapProducto(productoDTO));
    }

    private Producto mapProducto(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.nombre());
        producto.setPrecio(productoDTO.precio());
        producto.setStock(productoDTO.stock());
        producto.setDescripcion(productoDTO.descripcion());
        producto.setTipoProducto(productoDTO.tipoProducto());
        return producto;
    }

}
