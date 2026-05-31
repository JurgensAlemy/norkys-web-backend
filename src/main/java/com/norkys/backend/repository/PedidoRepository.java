package com.norkys.backend.repository;

import com.norkys.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId);
    Optional<Pedido> findByCodigo(String codigo);
    List<Pedido> findAllByOrderByFechaCreacionDesc();
}