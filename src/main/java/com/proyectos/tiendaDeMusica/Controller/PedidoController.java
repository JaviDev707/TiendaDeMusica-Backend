package com.proyectos.tiendaDeMusica.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.tiendaDeMusica.Entity.Pedido;
import com.proyectos.tiendaDeMusica.Service.PedidoService;
import com.proyectos.tiendaDeMusica.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    
    private final PedidoService pedidoService;
    private final UserService userService;

    @PostMapping("/checkout")
    public ResponseEntity<Pedido> checkout(Principal principal) {
        Long usuarioId = getUsuarioId(principal);
        Pedido nuevoPedido = pedidoService.realizarCheckout(usuarioId);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Pedido>> historialPedidos(Principal principal) {
        Long usuarioId = getUsuarioId(principal);
        return ResponseEntity.ok(pedidoService.obtenerHistorialPedidos(usuarioId));
    }

    @GetMapping("/detalle/{pedidoId}")
    public ResponseEntity<Pedido> obtenerDetallePedido(@PathVariable Long pedidoId, Principal principal) {
        Long usuarioId = getUsuarioId(principal);
        return ResponseEntity.ok(pedidoService.obtenerDetallePedido(pedidoId, usuarioId));
    }

    private Long getUsuarioId(Principal principal) {
        return userService.buscarPorEmail(principal.getName()).getId();
    }

}
