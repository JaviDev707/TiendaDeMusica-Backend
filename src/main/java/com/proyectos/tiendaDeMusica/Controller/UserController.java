package com.proyectos.tiendaDeMusica.Controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.tiendaDeMusica.Entity.Usuario;
import com.proyectos.tiendaDeMusica.Service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @RequestMapping()
    public ResponseEntity<Usuario> obtenerUsuario(Principal principal) {
        Long usuarioId = userService.buscarPorEmail(principal.getName()).getId();
        return ResponseEntity.ok(userService.buscarPorId(usuarioId));
    }

}
