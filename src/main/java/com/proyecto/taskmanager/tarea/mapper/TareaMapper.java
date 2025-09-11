package com.proyecto.taskmanager.tarea.mapper;

import com.proyecto.taskmanager.proyecto.model.Proyecto;
import com.proyecto.taskmanager.tarea.dto.TareaCreateDto;
import com.proyecto.taskmanager.tarea.dto.TareaDto;
import com.proyecto.taskmanager.tarea.dto.TareaUpdateDto;
import com.proyecto.taskmanager.tarea.model.Tarea;
import com.proyecto.taskmanager.usuario.model.Usuario;

import java.time.Instant;

/**
 * Mapper manual para Tarea.
 * <p>
 *  - Para crear/actualizar una Tarea, el servicio debe resolver las entidades Proyecto y Usuario
 *    (por ejemplo: proyectoRepository.findById(dto.getProyectoId())). Este mapper recibe esas entidades
 *    ya resueltas como parámetros.
 *  - No hace llamadas a repositorios.
 * </p>
 */
public class TareaMapper {

    private TareaMapper() {}

    /**
     * Crea una entidad Tarea a partir del DTO y las entidades relacionadas resueltas.
     *
     * @param tareaCreateDto DTO de creación
     * @param proyecto entidad Proyecto (puede ser null)
     * @param asignado entidad Usuario asignado (puede ser null)
     * @return Tarea lista para persistir
     */
    public static Tarea toEntity(TareaCreateDto tareaCreateDto, Proyecto proyecto, Usuario asignado) {
        if (tareaCreateDto == null) return null;

        Instant now = Instant.now();
        Tarea.TareaBuilder builder = Tarea.builder()
                .titulo(tareaCreateDto.getTitulo())
                .descripcion(tareaCreateDto.getDescripcion())
                .prioridad(tareaCreateDto.getPrioridad())
                .fechavencimiento(tareaCreateDto.getFechavencimiento())
                .completada(Boolean.FALSE)
                .proyecto(proyecto)
                .asignadoA(asignado);

        return builder.build();
    }

    /**
     * Convierte entidad Tarea -> TareaDto.
     *
     * @param t entidad Tarea
     * @return TareaDto
     */
    public static TareaDto fromEntity(Tarea t) {
        if (t == null) return null;
        return TareaDto.builder()
                .id(t.getId())
                .titulo(t.getTitulo())
                .descripcion(t.getDescripcion())
                .prioridad(t.getPrioridad())
                .fechavencimiento(t.getFechavencimiento())
                .completada(t.getCompletada())
                .proyectoId(t.getProyecto() != null ? t.getProyecto().getId() : null)
                .asignadoAId(t.getAsignadoA() != null ? t.getAsignadoA().getId() : null)
                .build();
    }

    /**
     * Actualiza una entidad Tarea existente con datos del DTO (parcial).
     * <p>El servicio debe haber resuelto y pasado el proyecto y usuario adecuados si el DTO pide cambio.</p>
     *
     * @param tarea entidad a actualizar
     * @param tareaUpdateDto DTO de actualización (parcial)
     * @param proyecto entidad Proyecto ya resuelta/obtenida por el servicio (puede pasar null si no se cambia)
     * @param asignado entidad Usuario ya resuelta/obtenida por el servicio (puede pasar null si no se cambia)
     */
    public static void updateEntityFromDto(Tarea tarea, TareaUpdateDto tareaUpdateDto, Proyecto proyecto, Usuario asignado) {
        if (tarea == null || tareaUpdateDto == null) return;
        if (tareaUpdateDto.getTitulo() != null) tarea.setTitulo(tareaUpdateDto.getTitulo());
        if (tareaUpdateDto.getDescripcion() != null) tarea.setDescripcion(tareaUpdateDto.getDescripcion());
        if (tareaUpdateDto.getPrioridad() != null) tarea.setPrioridad(tareaUpdateDto.getPrioridad());
        if (tareaUpdateDto.getFechavencimiento() != null) tarea.setFechavencimiento(tareaUpdateDto.getFechavencimiento());
        if (tareaUpdateDto.getCompletada() != null) tarea.setCompletada(tareaUpdateDto.getCompletada());

        // Si el DTO solicita cambio de proyecto/asignado, el servicio debe pasar las entidades resueltas.
        if (tareaUpdateDto.getProyectoId() != null) tarea.setProyecto(proyecto);
        if (tareaUpdateDto.getAsignadoAId() != null) tarea.setAsignadoA(asignado);
    }
}
