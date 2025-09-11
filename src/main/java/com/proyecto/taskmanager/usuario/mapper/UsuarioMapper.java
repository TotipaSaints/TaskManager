package com.proyecto.taskmanager.usuario.mapper;

import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import com.proyecto.taskmanager.usuario.enums.Rol;
import com.proyecto.taskmanager.usuario.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mapper manual para Usuario.
 * <p>
 *  - No expone el campo password en los DTOs de salida.
 *  - Para crear/actualizar, recibe un PasswordEncoder para hashear la contraseña.
 *  - No realiza operaciones de repositorio (esas van en el servicio).
 * </p>
 */
public class UsuarioMapper {

    private UsuarioMapper() {}

    /**
     * Convierte com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto a entidad Usuario (hash de password incluido).
     *
     * @param dto DTO de creación (password en texto plano)
     * @param encoder PasswordEncoder para hashear la contraseña
     * @return entidad Usuario lista para persistir
     */
    public static Usuario toEntity(UsuarioCreateDto dto, PasswordEncoder encoder) {
        if (dto == null) return null;
        String hashed = dto.getPassword() != null ? encoder.encode(dto.getPassword()) : null;
        Usuario.UsuarioBuilder builder = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .password(hashed)
                .activo(true);

        if (dto.getRol() != null) {
            builder.rol(dto.getRol());
        } else {
            builder.rol(Rol.USER);
        }

        return builder.build();
    }

    /**
     * Convierte entidad Usuario a UsuarioDto (sin password).
     *
     * @param u entidad Usuario
     * @return UsuarioDto público
     */
    public static UsuarioDto toDto(Usuario u) {
        if (u == null) return null;
        return UsuarioDto.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .email(u.getEmail())
                .rol(u.getRol())
                .activo(u.getActivo())
                .fechaCreacion(u.getFechaCreacion())
                .build();
    }

    /**
     * Aplica cambios parciales desde com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto sobre la entidad existente.
     * <p>Campos null en el DTO se ignoran. Si password no es null se hashea.</p>
     *
     * @param u entidad a actualizar (no nula)
     * @param dto DTO con los cambios (parcial)
     * @param encoder PasswordEncoder para hashear la contraseña si viene
     */
    public static void updateEntityFromDto(Usuario u, UsuarioUpdateDto dto, PasswordEncoder encoder) {
        if (u == null || dto == null) return;
        if (dto.getNombre() != null) u.setNombre(dto.getNombre());
        if (dto.getEmail() != null) u.setEmail(dto.getEmail());
        if (dto.getPassword() != null) u.setPassword(encoder.encode(dto.getPassword()));
        if (dto.getRol() != null) u.setRol(dto.getRol());
        if (dto.getActivo() != null) u.setActivo(dto.getActivo());
    }
}