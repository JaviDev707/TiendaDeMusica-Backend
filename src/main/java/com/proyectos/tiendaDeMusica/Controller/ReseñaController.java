package com.proyectos.tiendaDeMusica.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectos.tiendaDeMusica.DTO.ReseñaDTO;
import com.proyectos.tiendaDeMusica.Entity.Reseña;
import com.proyectos.tiendaDeMusica.Service.ReseñaService;
import com.proyectos.tiendaDeMusica.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reseña")
@RequiredArgsConstructor
public class ReseñaController {
    
    private final ReseñaService reseñaService;
    private final UserService userService;

    @PostMapping("/crear")
    public ResponseEntity<Reseña> nuevaReseña (@RequestBody ReseñaDTO reseñaDTO, Principal  principal) {
        Long idUsuario = userService.buscarPorEmail(principal.getName()).getId();
        Reseña reseña = reseñaService.crearReseña(reseñaDTO, idUsuario);
        return new ResponseEntity<>(reseña,  HttpStatus.CREATED);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<Reseña>> reseñasProducto(@PathVariable Long idProducto) {
        List<Reseña> reseñas = reseñaService.obtenerReseñaPorProducto(idProducto);
        return new ResponseEntity<>(reseñas, HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Reseña>> reseñasUsuario(@PathVariable Long idUsuario) {
        List<Reseña> reseñas = reseñaService.obtenerReseñaPorUsuario(idUsuario);
        return new ResponseEntity<>(reseñas, HttpStatus.OK);
    }

}
