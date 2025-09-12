package com.proyecto.taskmanager.tarea.controller;
import com.proyecto.taskmanager.tarea.dto.TareaCreateDto;
import com.proyecto.taskmanager.tarea.dto.TareaDto;
import com.proyecto.taskmanager.tarea.dto.TareaUpdateDto;
import com.proyecto.taskmanager.tarea.service.TareaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Endpoints REST para tareas.
 */
@Tag(name = "Tareas", description = "Operaciones para gestionar tareas")
@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    /**
     * Crea una tarea.
     */
    @Operation(
            summary = "Crear tarea",
            description = "Crea una nueva tarea. Puede asociarse a un proyecto y a un usuario (pasar IDs). Ejemplos con temática Pokémon.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO con datos para crear la tarea",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TareaCreateDto.class),
                            examples = {
                                    @ExampleObject(name = "moltres-captura", value = """
                        {
                          "titulo": "Capturar muestras - Moltres",
                          "descripcion": "Recolectar imágenes térmicas en la zona volcánica",
                          "prioridad": "ALTA",
                          "fechavencimiento": "2025-10-01",
                          "proyectoId": 10,
                          "asignadoAId": 2
                        }
                        """),
                                    @ExampleObject(name = "articuno-patrulla", value = """
                        {
                          "titulo": "Patrullar cielo - Articuno",
                          "descripcion": "Vigilar migraciones de aves en altitud",
                          "prioridad": "MEDIA",
                          "fechavencimiento": "2025-10-15",
                          "proyectoId": 5,
                          "asignadoAId": 3
                        }
                        """)
                            }
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Tarea creada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaDto.class)))
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @PostMapping
    public ResponseEntity<TareaDto> create(@Valid @RequestBody TareaCreateDto tareaCreateDto) {
        TareaDto created = tareaService.create(tareaCreateDto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Lista todas las tareas.
     */
    @Operation(summary = "Listar tareas",
            description = "Devuelve la lista de tareas. Se recomienda paginación si hay muchos registros.")
    @ApiResponse(responseCode = "200", description = "Lista de tareas",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TareaDto.class)),
                    examples = @ExampleObject(value = """
                [
                  {
                    "id": 55,
                    "titulo": "Patrullar cielo - Articuno",
                    "descripcion": "Vigilar migraciones de aves en altitud",
                    "prioridad": "MEDIA",
                    "fechavencimiento": "2025-10-15",
                    "completada": false,
                    "proyectoId": 5,
                    "asignadoAId": 3
                  },
                  {
                    "id": 101,
                    "titulo": "Inspección eléctrica - Zapdos",
                    "descripcion": "Probar generadores y líneas de alta tensión",
                    "prioridad": "ALTA",
                    "fechavencimiento": "2025-09-30",
                    "completada": false,
                    "proyectoId": 10,
                    "asignadoAId": 4
                  }
                ]
                """
                    )
            )
    )
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @GetMapping
    public ResponseEntity<List<TareaDto>> findAll() {
        return ResponseEntity.ok(tareaService.findAll());
    }

    /**
     * Obtiene tarea por id.
     */
    @Operation(summary = "Obtener tarea por id",
            description = "Obtiene una tarea por su identificador.")
    @ApiResponse(responseCode = "200", description = "Tarea encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 55,
                  "titulo": "Patrullar cielo - Articuno",
                  "descripcion": "Vigilar migraciones de aves en altitud",
                  "prioridad": "MEDIA",
                  "fechavencimiento": "2025-10-15",
                  "completada": false,
                  "proyectoId": 5,
                  "asignadoAId": 3
                }
                """
                    )
            )
    )
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content)
    @GetMapping("/{id}")
    public ResponseEntity<TareaDto> findById(@Parameter(description = "ID de la tarea", required = true, example = "55") @PathVariable Long id) {
        return ResponseEntity.ok(tareaService.findById(id));
    }

    /**
     * Actualiza una tarea parcialmente.
     */
    @Operation(summary = "Actualizar tarea",
            description = "Actualiza campos de la tarea; campos nulos son ignorados.")
    @ApiResponse(responseCode = "200", description = "Tarea actualizada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TareaDto.class),
                    examples = @ExampleObject(value = """
                {
                  "id": 55,
                  "titulo": "Patrullar cielo - Articuno",
                  "descripcion": "Vigilar migraciones de aves en altitud",
                  "prioridad": "MEDIA",
                  "fechavencimiento": "2025-10-15",
                  "completada": true,
                  "proyectoId": 5,
                  "asignadoAId": 3
                }
                """
                    )
            )
    )
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content)
    @PatchMapping("/{id}")
    public ResponseEntity<TareaDto> update(@PathVariable Long id, @Valid @RequestBody TareaUpdateDto tareaUpdateDto) {
        return ResponseEntity.ok(tareaService.update(id, tareaUpdateDto));
    }

    /**
     * Elimina una tarea.
     */
    @Operation(summary = "Eliminar tarea",
            description = "Elimina una tarea por id.")
    @ApiResponse(responseCode = "204", description = "Tarea eliminada")
    @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content)
    @ApiResponse(responseCode = "403", description = "Prohibido (rol insuficiente)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada", content = @Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID de la tarea", required = true, example = "55") @PathVariable Long id) {
        tareaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
