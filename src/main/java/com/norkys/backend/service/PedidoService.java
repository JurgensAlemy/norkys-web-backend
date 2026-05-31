package com.norkys.backend.service;

import com.norkys.backend.dto.CrearPedidoRequest;
import com.norkys.backend.entity.*;
import com.norkys.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    @Transactional
    public Pedido crear(CrearPedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .direccionEntrega(request.getDireccionEntrega())
                .estado("Pendiente")
                .build();

        // Construir detalles
        List<DetallePedido> detalles = request.getItems().stream().map(item -> {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));
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
                .sum() + 5.0; // costo envío

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