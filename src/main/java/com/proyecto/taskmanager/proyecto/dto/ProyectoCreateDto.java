package com.proyecto.taskmanager.proyecto.dto;

import com.proyecto.taskmanager.proyecto.enums.EstadoProyecto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoCreateDto {
    @NotBlank
    private String nombre;

    private String descripcion;

    @NotNull
    private Instant fechainicio;

    private Instant fechafin;

    private EstadoProyecto estado;
}
