package com.norkys.backend.controller;

import com.norkys.backend.dto.CrearPedidoRequest;
import com.norkys.backend.entity.Pedido;
import com.norkys.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearPedidoRequest request) {
        try { return ResponseEntity.ok(pedidoService.crear(request)); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> porUsuario(@PathVariable Long usuarioId) { return pedidoService.obtenerPorUsuario(usuarioId); }

    @GetMapping
    public List<Pedido> todos() { return pedidoService.obtenerTodos(); }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try { return ResponseEntity.ok(pedidoService.cambiarEstado(id, body.get("estado"))); }
        catch (RuntimeException e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}