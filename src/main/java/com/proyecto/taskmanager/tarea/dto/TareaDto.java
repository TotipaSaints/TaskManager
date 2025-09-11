package com.proyecto.taskmanager.tarea.dto;

import com.proyecto.taskmanager.tarea.enums.Prioridad;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private Instant fechavencimiento;
    private Boolean completada;
    private Long proyectoId;
    private Long asignadoAId;
}

