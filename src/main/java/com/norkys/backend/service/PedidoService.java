package com.norkys.backend.service;

import com.norkys.backend.dto.CrearPedidoRequest;
import com.norkys.backend.entity.*;
import com.norkys.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    private static final Set<String> METODOS_VALIDOS = Set.of("Efectivo", "Yape", "Plin", "Tarjeta");

    @Transactional
    public Pedido crear(CrearPedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }
        if (request.getDireccionEntrega() == null || request.getDireccionEntrega().isBlank()) {
            throw new RuntimeException("Debes indicar una dirección de entrega");
        }

        String metodoPago = request.getMetodoPago();
        if (metodoPago == null || !METODOS_VALIDOS.contains(metodoPago)) {
            metodoPago = "Efectivo";
        }

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .direccionEntrega(request.getDireccionEntrega())
                .metodoPago(metodoPago)
                .estado("Pendiente")
                .build();

        // ===== NUEVO: validar stock ANTES de construir los detalles =====
        for (CrearPedidoRequest.ItemPedido item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));
            if (producto.getStock() == null || producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Sin stock suficiente para: " + producto.getNombre()
                        + " (disponible: " + (producto.getStock() == null ? 0 : producto.getStock()) + ")");
            }
        }

        // Construir detalles
        List<DetallePedido> detalles = request.getItems().stream().map(item -> {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));

            // ===== NUEVO: restar stock =====
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            return DetallePedido.builder()
                    .pedido(pedido)
                    .producto(producto)
                    .cantidad(item.getCantidad())
                    .precioUnitario(producto.getPrecio())
                    .build();
        }).collect(Collectors.toList());

        pedido.setDetalles(detalles);

        double total = detalles.stream()
                .mapToDouble(d -> d.getPrecioUnitario() * d.getCantidad())
                .sum() + 5.0;

        pedido.setTotal(total);
        pedido.setCodigo("NK-" + System.currentTimeMillis() % 100000);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAllByOrderByFechaCreacionDesc();
    }

    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}