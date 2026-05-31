package com.norkys.backend.service;

import com.norkys.backend.dto.LoginRequest;
import com.norkys.backend.dto.LoginResponse;
import com.norkys.backend.entity.Usuario;
import com.norkys.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        // Por ahora comparación directa — luego migraremos a BCrypt
        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        if (!usuario.getActivo()) {
            throw new RuntimeException("Cuenta desactivada");
        }

        return LoginResponse.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .correo(usuario.getCorreo())
                .rol(usuario.getRol())
                .build();
    }

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("Ya existe una cuenta con ese correo");
        }
        usuario.setRol("cliente");
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }
}