package com.norkys.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.1", message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false)
    private String categoria;  // pollos, parrillas, combos, hamburguesas, bebidas, postres, porciones

    private String imgUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}