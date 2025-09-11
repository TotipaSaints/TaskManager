package com.proyecto.taskmanager.usuario.dto;

import com.proyecto.taskmanager.usuario.enums.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para crear un usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateDto {

    /** Nombre. Obligatorio. */
    @NotBlank
    @Size(max = 100)
    private String nombre;

    /** Email. Obligatorio y con formato. */
    @NotBlank
    @Email
    private String email;

    /** Contraseña en texto plano solo en este DTO de entrada; se debe hashear antes de persistir. */
    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    /** Rol opcional; si no viene se usa USER. */
    private Rol rol;
}
