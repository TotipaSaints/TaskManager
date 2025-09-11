package com.proyecto.taskmanager.proyecto.mapper;

import com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto;
import com.proyecto.taskmanager.proyecto.dto.ProyectoDto;
import com.proyecto.taskmanager.proyecto.enums.EstadoProyecto;
import com.proyecto.taskmanager.proyecto.model.Proyecto;

import java.time.Instant;

/**
 * Mapper manual para Proyecto.
 * <p>
 *  - Asume que com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto/ProyectoDto usan {@link Instant} para fechas (fechaInicio, fechaFin).
 *  - No realiza búsquedas en BD; el servicio debe encargarse de eso.
 * </p>
 */
public class ProyectoMapper {

    private ProyectoMapper() {}

    /**
     * Convierte com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto -> Proyecto (nuevo).
     *
     * @param proyectoCreateDto DTO de creación
     * @return entidad Proyecto
     */
    public static Proyecto toEntity(ProyectoCreateDto proyectoCreateDto) {
        if (proyectoCreateDto == null) return null;

        Instant now = Instant.now();
        Proyecto.ProyectoBuilder builder = Proyecto.builder()
                .nombre(proyectoCreateDto.getNombre())
                .descripcion(proyectoCreateDto.getDescripcion())
                .fechaInicio(proyectoCreateDto.getFechainicio() != null ? proyectoCreateDto.getFechainicio() : now)
                .fechaFin(proyectoCreateDto.getFechafin())
                .estado(proyectoCreateDto.getEstado() != null ? proyectoCreateDto.getEstado() : EstadoProyecto.ACTIVO);

        return builder.build();
    }

    /**
     * Convierte Proyecto -> ProyectoDto.
     *
     * @param p entidad Proyecto
     * @return ProyectoDto
     */
    public static ProyectoDto toDto(Proyecto p) {
        if (p == null) return null;
        return ProyectoDto.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .fechainicio(p.getFechaInicio())
                .fechafin(p.getFechaFin())
                .estado(p.getEstado())
                .build();
    }

    /**
     * Actualiza una entidad Proyecto existente con valores del DTO (parcial).
     *
     * @param p entidad a actualizar
     * @param dto DTO con nuevos valores (campos null son ignorados)
     */
    public static void updateFromDto(Proyecto p, ProyectoCreateDto dto) {
        if (p == null || dto == null) return;
        if (dto.getNombre() != null) p.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) p.setDescripcion(dto.getDescripcion());
        if (dto.getFechainicio() != null) p.setFechaInicio(dto.getFechainicio());
        if (dto.getFechafin() != null) p.setFechaFin(dto.getFechafin());
        if (dto.getEstado() != null) p.setEstado(dto.getEstado());
    }
}
