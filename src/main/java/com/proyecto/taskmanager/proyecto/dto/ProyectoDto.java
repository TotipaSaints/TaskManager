package com.proyecto.taskmanager.proyecto.dto;

import com.proyecto.taskmanager.proyecto.enums.EstadoProyecto;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProyectoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private Instant fechainicio;
    private Instant fechafin;
    private EstadoProyecto estado;
}
