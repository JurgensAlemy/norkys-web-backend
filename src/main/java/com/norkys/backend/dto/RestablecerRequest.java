package com.norkys.backend.dto;

import lombok.Data;

@Data
public class RestablecerRequest {
    private String correo;
    private String codigo;
    private String nuevaPassword;
}