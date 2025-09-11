package com.proyecto.taskmanager.auth.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para petición de inicio de sesión.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InicioSesionDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
