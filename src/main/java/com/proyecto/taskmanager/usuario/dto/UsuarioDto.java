package com.proyecto.taskmanager.usuario.dto;

import com.proyecto.taskmanager.usuario.enums.Rol;
import lombok.*;

import java.time.Instant;

/**
 * DTO de salida para representar un usuario (sin password).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {
    /** Identificador. */
    private Long id;

    /** Nombre del usuario. */
    private String nombre;

    /** Correo electrónico. */
    private String email;

    /** Rol del usuario. */
    private Rol rol;

    /** Indicador si el usuario está activo. */
    private Boolean activo;

    /** Fecha de creación. */
    private Instant fechaCreacion;
}
