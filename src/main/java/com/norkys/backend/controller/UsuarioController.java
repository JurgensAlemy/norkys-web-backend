package com.norkys.backend.controller;

import com.norkys.backend.entity.Usuario;
import com.norkys.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    // GET /api/usuarios
    @GetMapping
    public List<Usuario> todos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(u -> u.setPassword(null));
        return usuarios;
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public Usuario porId(@PathVariable Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        u.setPassword(null);
        return u;
    }

    // PUT /api/usuarios/{id} — actualizar nombres, apellidos, celular
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, String> datos) {

        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (datos.containsKey("nombres"))
                        usuario.setNombres(datos.get("nombres"));
                    if (datos.containsKey("apellidos"))
                        usuario.setApellidos(datos.get("apellidos"));
                    if (datos.containsKey("celular"))
                        usuario.setCelular(datos.get("celular"));
                    Usuario actualizado = usuarioRepository.save(usuario);
                    actualizado.setPassword(null);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/usuarios/{id}/direcciones — agregar dirección
    @PostMapping("/{id}/direcciones")
    public ResponseEntity<?> agregarDireccion(
            @PathVariable Long id,
            @RequestBody Map<String, String> datos) {

        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (usuario.getDirecciones() == null)
                        usuario.setDirecciones(new ArrayList<>());
                    String alias = datos.getOrDefault("alias", "");
                    String detalle = datos.getOrDefault("detalle", "");
                    if (alias.isBlank() || detalle.isBlank())
                        return ResponseEntity.badRequest().body("alias y detalle son requeridos");
                    usuario.getDirecciones().add(alias + "|" + detalle);
                    Usuario actualizado = usuarioRepository.save(usuario);
                    actualizado.setPassword(null);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/usuarios/{id}/direcciones/{index} — eliminar dirección
    @DeleteMapping("/{id}/direcciones/{index}")
    public ResponseEntity<?> eliminarDireccion(
            @PathVariable Long id,
            @PathVariable int index) {

        return usuarioRepository.findById(id)
                .map(usuario -> {
                    List<String> dirs = usuario.getDirecciones();
                    if (dirs == null || index < 0 || index >= dirs.size())
                        return ResponseEntity.badRequest().body("Índice inválido");
                    dirs.remove(index);
                    Usuario actualizado = usuarioRepository.save(usuario);
                    actualizado.setPassword(null);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}