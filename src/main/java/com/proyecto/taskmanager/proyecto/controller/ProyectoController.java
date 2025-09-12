package com.proyecto.taskmanager.proyecto.controller;
import com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto;
import com.proyecto.taskmanager.proyecto.dto.ProyectoDto;
import com.proyecto.taskmanager.proyecto.service.ProyectoService;
import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para proyectos.
 */
@Tag(name = "Proyectos", description = "Operaciones para gestionar proyectos")
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    /**
     * Crea un proyecto.
     */
    @Operation(
            summary = "Crear proyecto",
            description = "Crea un nuevo proyecto. Ejemplos usan nombres inspirados en Pokémon legendarios (p.ej. Proyecto Zapdos).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO con datos para crear el proyecto (fecha en ISO-8601).",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProyectoCreateDto.class),
                            examples = {
                                    @ExampleObject(name = "operacion-articuno", value = """
                        {
                          "nombre": "Operación Articuno",
                          "descripcion": "Expedición polar para estudiar corrientes de viento",
                          "fechaInicio": "2025-09-11T09:00:00Z",
                          "fechaFin": "2025-12-01T18:00:00Z",
                          "estado": "ACTIVO"
                        }
                        """),
                                    @ExampleObject(name = "proyecto-zapdos", value = """
                        {
                          "nombre": "Proyecto Zapdos",
                          "descripcion": "Red eléctrica experimental inspirada en Zapdos",
                          "fechaInicio": "2025-06-01T08:00:00Z",
                          "fechaFin": "2025-11-01T18:00:00Z",
                          "estado": "ACTIVO"
                        }
                        """)
                            }
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Proyecto creado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 10,
                  "nombre": "Proyecto Zapdos",
                  "descripcion": "Red eléctrica experimental inspirada en Zapdos",
                  "fechaInicio": "2025-06-01T08:00:00Z",
                  "fechaFin": "2025-11-01T18:00:00Z",
                  "estado": "ACTIVO"
                }
                """)
            ),
            headers = @Header(name = "Location", description = "URL del recurso creado", schema = @Schema(type = "string"))
    )
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @PostMapping
    public ResponseEntity<ProyectoDto> create(@Valid @RequestBody ProyectoCreateDto proyectoCreateDto) {
        ProyectoDto created = proyectoService.create(proyectoCreateDto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Lista proyectos.
     */
    @Operation(summary = "Listar proyectos",
            description = "Devuelve la lista de proyectos. Se recomienda paginación si hay muchos registros.")
    @ApiResponse(responseCode = "200", description = "Lista de proyectos",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProyectoDto.class)),
                    examples = @ExampleObject(value = """
                [
                  {
                    "id": 10,
                    "nombre": "Proyecto Zapdos",
                    "descripcion": "Red eléctrica experimental inspirada en Zapdos",
                    "fechaInicio": "2025-06-01T08:00:00Z",
                    "fechaFin": "2025-11-01T18:00:00Z",
                    "estado": "ACTIVO"
                  },
                  {
                    "id": 11,
                    "nombre": "Proyecto Moltres",
                    "descripcion": "Investigación térmica inspirada en Moltres",
                    "fechaInicio": "2025-07-01T08:00:00Z",
                    "fechaFin": "2025-10-30T18:00:00Z",
                    "estado": "INACTIVO"
                  }
                ]
                """
                    )
            )
    )
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @GetMapping
    public ResponseEntity<List<ProyectoDto>> findAll() {
        return ResponseEntity.ok(proyectoService.findAll());
    }

    /**
     * Obtiene proyecto por id.
     */
    @Operation(summary = "Obtener proyecto por id",
            description = "Obtiene un proyecto por su identificador.")
    @ApiResponse(responseCode = "200", description = "Proyecto encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 10,
                  "nombre": "Proyecto Zapdos",
                  "descripcion": "Red eléctrica experimental inspirada en Zapdos",
                  "fechaInicio": "2025-06-01T08:00:00Z",
                  "fechaFin": "2025-11-01T18:00:00Z",
                  "estado": "ACTIVO"
                }
                """)
            )
    )
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDto> findById(@Parameter(description = "ID del proyecto", required = true, example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    /**
     * Actualiza un proyecto.
     */
    @Operation(summary = "Actualizar proyecto",
            description = "Actualiza un proyecto existente. Campos nulos no se sobrescriben.")
    @ApiResponse(responseCode = "200", description = "Proyecto actualizado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProyectoDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 10,
                  "nombre": "Proyecto Moltres",
                  "descripcion": "Investigación térmica inspirada en Moltres",
                  "fechaInicio": "2025-07-01T08:00:00Z",
                  "fechaFin": "2025-10-30T18:00:00Z",
                  "estado": "ACTIVO"
                }
                """)
            )
    )
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDto> update(@Parameter(description = "ID del proyecto", required = true, example = "10") @PathVariable Long id,
                                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                      description = "DTO con datos a actualizar del proyecto.",
                                                      required = true,
                                                      content = @Content(schema = @Schema(implementation = ProyectoCreateDto.class),
                                                              examples = @ExampleObject(value = """
                                                                {
                                                                  "nombre": "Proyecto Moltres",
                                                                  "descripcion": "Investigación térmica inspirada en Moltres",
                                                                  "fechaInicio": "2025-07-01T08:00:00Z",
                                                                  "fechaFin": "2025-10-30T18:00:00Z",
                                                                  "estado": "ACTIVO"
                                                                }
                                                                """))
                                              )
                                              @Valid @RequestBody ProyectoCreateDto proyectoCreateDto) {
        return ResponseEntity.ok(proyectoService.update(id, proyectoCreateDto));
    }

    /**
     * Elimina un proyecto.
     */
    @Operation(summary = "Eliminar proyecto",
            description = "Elimina un proyecto por id. Asegúrate de gestionar tareas dependientes según la política del dominio.")
    @ApiResponse(responseCode = "204", description = "Proyecto eliminado")
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Proyecto no encontrado", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID del proyecto", required = true, example = "10") @PathVariable Long id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
