package com.proyecto.taskmanager.usuario.dto;

import com.proyecto.taskmanager.usuario.enums.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para actualizar un usuario (parcial).
 * <p>Todos los campos son opcionales; solo se actualizarán los que no sean null.</p>
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UsuarioUpdateDto {

    /** Nombre (opcional). */
    @Size(max = 100)
    private String nombre;

    /** Email (opcional). Si se envía, debe ser válido. */
    @Email
    private String email;

    /**
     * Nueva contraseña en texto plano (opcional).
     * <p>Si se proporciona, será hasheada antes de persistir.</p>
     */
    @Size(min = 8, max = 255)
    private String password;

    /** Rol opcional. */
    private Rol rol;

    /** Indica si el usuario está activo (opcional). */
    private Boolean activo;
}
