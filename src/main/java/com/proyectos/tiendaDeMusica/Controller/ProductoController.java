package com.proyectos.tiendaDeMusica.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.tiendaDeMusica.DTO.ProductoDTO;
import com.proyectos.tiendaDeMusica.Entity.Producto;
import com.proyectos.tiendaDeMusica.Service.ProductoService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    
    private final ProductoService productoService;
    
    @PostMapping("/crear")
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoDTO productoDTO) {
        Producto nuevoProducto = productoService.crearProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Producto>> listarTodosLosProductos() {
        List<Producto> productos = productoService.listarTodosLosProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProducto(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Producto> actualizarProducto(@RequestBody ProductoDTO productoDTO) {
        Producto productoActualizado = productoService.actualizarProducto(productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/crearlista")
    public ResponseEntity<List<Producto>> crearListaDeProductos(@RequestBody List<ProductoDTO> productosDTO) {
        List<Producto> nuevosProductos = productoService.crearListaDeProductos(productosDTO);
        return new ResponseEntity<>(nuevosProductos, HttpStatus.CREATED);
    }
}
