package com.norkys.backend.service;

import com.norkys.backend.dto.LoginRequest;
import com.norkys.backend.dto.LoginResponse;
import com.norkys.backend.dto.RestablecerRequest;
import com.norkys.backend.entity.Usuario;
import com.norkys.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    // ===== NUEVO: almacenamiento temporal de códigos de recuperación =====
    // correo -> [codigo, instanteExpiracion]
    private final ConcurrentHashMap<String, CodigoTemp> codigosRecuperacion = new ConcurrentHashMap<>();

    private record CodigoTemp(String codigo, Instant expira) {
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

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

    // ===== NUEVO: generar código de recuperación =====
    public String solicitarRecuperacion(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("No existe una cuenta con ese correo"));

        String codigo = String.valueOf(100000 + new SecureRandom().nextInt(900000)); // 6 dígitos
        codigosRecuperacion.put(correo, new CodigoTemp(codigo, Instant.now().plusSeconds(600))); // 10 min

        // En un proyecto real aquí se enviaría el correo (SMTP).
        // Para esta demo, devolvemos el código directamente al frontend.
        return codigo;
    }

    // ===== NUEVO: validar código y cambiar contraseña =====
    public void restablecerPassword(RestablecerRequest request) {
        CodigoTemp guardado = codigosRecuperacion.get(request.getCorreo());

        if (guardado == null || Instant.now().isAfter(guardado.expira())) {
            throw new RuntimeException("El código expiró o no es válido. Solicita uno nuevo.");
        }
        if (!guardado.codigo().equals(request.getCodigo())) {
            throw new RuntimeException("El código ingresado es incorrecto");
        }
        if (request.getNuevaPassword() == null || request.getNuevaPassword().length() < 4) {
            throw new RuntimeException("La nueva contraseña debe tener al menos 4 caracteres");
        }

        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPassword(request.getNuevaPassword());
        usuarioRepository.save(usuario);

        codigosRecuperacion.remove(request.getCorreo()); // el código ya no sirve
    }
}