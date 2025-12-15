package com.proyectos.tiendaDeMusica.Controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.tiendaDeMusica.DTO.ItemRequestDTO;
import com.proyectos.tiendaDeMusica.Entity.Carrito;
import com.proyectos.tiendaDeMusica.Service.CarritoService;
import com.proyectos.tiendaDeMusica.Service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CarritoController {
    
    private final CarritoService carritoService;  
    private final UserService userService;  

    @GetMapping("/")
    public ResponseEntity<Carrito> obtenerCarrito(Principal principal) {
        Long usuarioId = userService.buscarPorEmail(principal.getName()).getId();
        return ResponseEntity.ok(carritoService.obtenerCarrito(usuarioId));
    }

    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarItem(@RequestBody ItemRequestDTO itemRequest, Principal principal) {
        Long usuarioId = userService.buscarPorEmail(principal.getName()).getId();

        Carrito carritoActualizado = carritoService.agregarItem(
            usuarioId, 
            itemRequest.productoId(), 
            itemRequest.cantidad()
        );
        return ResponseEntity.ok(carritoActualizado);
    }

    @PutMapping("/actualizarcantidad")
    public ResponseEntity<Carrito> actualizarCantidad(@RequestBody ItemRequestDTO itemRequest, Principal principal) {
        Long usuarioId = userService.buscarPorEmail(principal.getName()).getId();

        Carrito carritoActualizado = carritoService.actualizarCantidad(
            usuarioId, 
            itemRequest.productoId(), 
            itemRequest.cantidad()
        );
        return ResponseEntity.ok(carritoActualizado);
    }

    @DeleteMapping("/eliminaritem/{productoid}")
    public ResponseEntity<Carrito> eliminarItem(@PathVariable Long productoid, Principal principal) {
        Long usuarioId = userService.buscarPorEmail(principal.getName()).getId();

        Carrito carritoActualizado = carritoService.quitarItem(usuarioId, productoid);
        return ResponseEntity.ok(carritoActualizado);
    }

}
