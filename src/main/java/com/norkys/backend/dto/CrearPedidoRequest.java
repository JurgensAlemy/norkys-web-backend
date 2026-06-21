package com.norkys.backend.dto;
import lombok.Data;
import java.util.List;

@Data
public class CrearPedidoRequest {
    private Long usuarioId;
    private String direccionEntrega;

    // ===== NUEVO =====
    // "Efectivo" | "Yape" | "Plin" | "Tarjeta"
    private String metodoPago;

    private List<ItemPedido> items;

    @Data
    public static class ItemPedido {
        private Long productoId;
        private Integer cantidad;
    }
}
