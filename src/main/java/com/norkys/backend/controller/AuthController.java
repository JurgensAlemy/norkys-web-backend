package com.norkys.backend.controller;

import com.norkys.backend.dto.LoginRequest;
import com.norkys.backend.dto.LoginResponse;
import com.norkys.backend.entity.Usuario;
import com.norkys.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try { return ResponseEntity.ok(authService.login(request)); }
        catch (RuntimeException e) { return ResponseEntity.status(401).body(e.getMessage()); }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        try { Usuario nuevo = authService.registrar(usuario); nuevo.setPassword(null); return ResponseEntity.ok(nuevo); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}