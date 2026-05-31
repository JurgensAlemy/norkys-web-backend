package com.norkys.backend.dto;
import lombok.*;

@Data @Builder
public class LoginResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String rol;
}