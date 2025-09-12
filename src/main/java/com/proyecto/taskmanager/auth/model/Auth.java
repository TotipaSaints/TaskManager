package com.proyecto.taskmanager.auth.model;

import com.proyecto.taskmanager.usuario.dto.UsuarioDto;
import lombok.*;

import java.time.Instant;

/**
 * DTO de respuesta tras autenticación exitosa.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {
    /** Token JWT. */
    private String token;

    /** Tipo de token, p.ej. "Bearer" */
    private String tokenType;

    /** Fecha de expiración del token (epoch millis o ISO). */
    private Instant expiresAt;

    /** Info básica del usuario autenticado (sin password). */
    private UsuarioDto usuario;
}
