package com.proyectos.tiendaDeMusica.Service;

import com.proyectos.tiendaDeMusica.Config.JwtUtil;
import com.proyectos.tiendaDeMusica.DTO.AuthResponse;
import com.proyectos.tiendaDeMusica.DTO.LoginRequestDTO;
import com.proyectos.tiendaDeMusica.DTO.RegisterRequestDTO;
import com.proyectos.tiendaDeMusica.Entity.Usuario;
import com.proyectos.tiendaDeMusica.Enums.Role;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService; 
    private final PasswordEncoder passwordEncoder; 
    private final AuthenticationManager authenticationManager; 
    private final JwtUtil jwtUtil; 

    @Transactional
    public AuthResponse registrar(RegisterRequestDTO nuevoUsuario) {

        if (userService.buscarPorEmail(nuevoUsuario.email()) != null) {
            throw new RuntimeException("El email ya esta en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(nuevoUsuario.email());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.password()));
        usuario.setNombre(nuevoUsuario.nombre());
        usuario.setApellido(nuevoUsuario.apellido());
        usuario.setCodigoPostal(nuevoUsuario.codigoPostal());
        usuario.setLocalidad(nuevoUsuario.localidad());
        usuario.setDireccion(nuevoUsuario.direccion());
        usuario.setRole(Role.USER);
        
        usuario = userService.guardarUsuario(usuario);

        String token = jwtUtil.generateToken(usuario);
        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse login(LoginRequestDTO request) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        
        Usuario usuario = userService.buscarPorEmail(request.email());

        String token = jwtUtil.generateToken(usuario);
        return new AuthResponse(token);
    }
}