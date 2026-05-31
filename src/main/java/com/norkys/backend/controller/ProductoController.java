package com.norkys.backend.controller;

import com.norkys.backend.entity.Producto;
import com.norkys.backend.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public List<Producto> obtenerTodos() { return productoService.obtenerTodos(); }

    @GetMapping(params = "categoria")
    public List<Producto> porCategoria(@RequestParam String categoria) { return productoService.obtenerPorCategoria(categoria); }

    @GetMapping("/buscar")
    public List<Producto> buscar(@RequestParam String q) { return productoService.buscar(q); }

    @GetMapping("/{id}")
    public Producto porId(@PathVariable Long id) { return productoService.obtenerPorId(id); }

    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) { return ResponseEntity.ok(productoService.crear(producto)); }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) { return ResponseEntity.ok(productoService.actualizar(id, producto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) { productoService.eliminar(id); return ResponseEntity.noContent().build(); }
}