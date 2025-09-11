package com.proyecto.taskmanager.usuario.model;

import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import com.proyecto.taskmanager.usuario.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Representa un usuario del sistema.
 * <p>
 *  - El campo {@code password} debe contener el hash (bcrypt/argon2), no la contraseña en claro.
 *  - {@code email} es único y se recomienda almacenar como {@code citext} en DB para case-insensitive.
 * </p>
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    /** Identificador único (PK). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre completo del usuario. */
    @Column(nullable = false, length = 100)
    private String nombre;

    /** Correo electrónico único del usuario. */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hash de la contraseña.
     * <p>No exponer en DTOs de respuesta.</p>
     */
    @Column(nullable = false, length = 255)
    private String password;

    /** Rol del usuario en la aplicación. */
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 20)
    private Rol rol = Rol.USER;

    /** Indica si el usuario está activo. */
    @Column(nullable = false)
    private Boolean activo = Boolean.TRUE;

    /** Fecha de creación del usuario (timestamptz). */
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    /** Metodo que transforma el CreateDto a Modelo*/
    public static Usuario fromCreateDto(UsuarioCreateDto usuarioCreateDto) {
        if (usuarioCreateDto == null) {
            return null;
        }

        return Usuario.builder().nombre(usuarioCreateDto.getNombre()).email(usuarioCreateDto.getEmail()).password(usuarioCreateDto.getPassword()).rol(usuarioCreateDto.getRol()).build();

    }

    /** Metodo que transforma el UpdateDto a Modelo*/
    public static Usuario fromUpdateDto(UsuarioUpdateDto usuarioCreateDto) {
        if (usuarioCreateDto == null) {
            return null;
        }

        return Usuario.builder().nombre(usuarioCreateDto.getNombre()).email(usuarioCreateDto.getEmail()).password(usuarioCreateDto.getPassword()).rol(usuarioCreateDto.getRol()).build();

    }

    /**
     * Se ejecuta antes de persistir para fijar la fecha de creación.
     */
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = Instant.now();
    }
}