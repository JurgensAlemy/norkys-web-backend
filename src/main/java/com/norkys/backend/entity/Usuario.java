package com.norkys.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombres;

    @NotBlank
    @Column(nullable = false)
    private String apellidos;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String correo;

    @NotBlank
    @Column(nullable = false)
    private String password;

    private String celular;

    @Column(nullable = false)
    @Builder.Default
    private String rol = "cliente";

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @ElementCollection
    @CollectionTable(name = "usuario_direcciones", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "direccion")
    @Builder.Default
    private List<String> direcciones = new ArrayList<>();
}