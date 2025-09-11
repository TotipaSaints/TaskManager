package com.proyecto.taskmanager.tarea.dto;

import com.proyecto.taskmanager.tarea.enums.Prioridad;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TareaCreateDto {
    @NotBlank
    private String titulo;

    private String descripcion;

    @NotNull
    private Prioridad prioridad;

    @NotNull
    private Instant fechavencimiento;

    private Long proyectoId;

    private Long asignadoAId;
}
