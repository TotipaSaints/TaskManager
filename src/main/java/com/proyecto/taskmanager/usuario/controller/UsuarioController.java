package com.proyecto.taskmanager.usuario.controller;

import com.proyecto.taskmanager.usuario.dto.UsuarioDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import com.proyecto.taskmanager.usuario.service.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Endpoints REST para gestión de usuarios.
 */
@Tag(name = "Usuarios", description = "Operaciones para gestionar usuarios")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/usuario")
public class UsuarioController {

    /**
     * ToDo
     * Anotaciones Swagger Completas - Examples de error 200-201-400-401
     * Mofifcar respuestas {@link org.springframework.http.ResponseEntity} \*
     * JavaDocs Lvl Alto
     * Uso de {@link org.apache.commons.lang3.ClassUtils.Interfaces}
     *
     */
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Operation(summary = "Listar usuarios",
            description = "Devuelve la lista de usuarios (sin password).")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UsuarioDto.class)),
                    examples = @ExampleObject(value = """
                [
                  {
                    "id": 1,
                    "nombre": "Bulbasaur",
                    "email": "bulbasaur@ejemplo.com",
                    "rol": "USER",
                    "activo": true,
                    "fechaCreacion": "2025-09-11T15:00:00Z"
                  },
                  {
                    "id": 2,
                    "nombre": "Charmander",
                    "email": "charmander@ejemplo.com",
                    "rol": "USER",
                    "activo": true,
                    "fechaCreacion": "2025-09-11T15:10:00Z"
                  }
                ]
                """
                    )
            )
    )
    @GetMapping("/listar")
    public List<Usuario> listar() {
        return this.usuarioServiceImpl.listarUsuario();
    }

    @Operation(summary = "Obtener usuario por id",
            description = "Obtiene un usuario por su identificador.")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 3,
                  "nombre": "Pikachu",
                  "email": "pikachu@ejemplo.com",
                  "rol": "USER",
                  "activo": true,
                  "fechaCreacion": "2025-09-11T15:05:00Z"
                }
                """
                    )
            )
    )
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @GetMapping("/buscar/{id}")
    public Optional<Usuario> buscar(@Parameter(description = "ID del usuario", required = true, example = "1") @PathVariable Long id) {
        return this.usuarioServiceImpl.buscarUsuario(id);
    }

    @Operation(summary = "Eliminar usuario",
            description = "Elimina un usuario por id (operación irreversible). Requiere rol ADMIN.")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @DeleteMapping("/eliminar/{id}")
    public Boolean eliminar(@PathVariable Long id) {
        return this.usuarioServiceImpl.eliminarUsuario(id);
    }

    @Operation(summary = "Actualizar usuario (parcial)",
            description = "Actualiza campos del usuario. Campos nulos no se modifican. Solo ADMIN o el propio usuario pueden modificar.")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 2,
                  "nombre": "Eevee",
                  "email": "eevee@ejemplo.com",
                  "rol": "USER",
                  "activo": true,
                  "fechaCreacion": "2025-09-11T15:20:00Z"
                }
                """
                    )
            )
    )
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    @PutMapping("/{id}")
    public Usuario modificar(@PathVariable Long id,
                             @RequestBody UsuarioUpdateDto usuarioUpdateDto) {
        return this.usuarioServiceImpl.modificarUsuario(id, usuarioUpdateDto);
    }

}
