package com.proyecto.taskmanager.proyecto.service;

import com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto;
import com.proyecto.taskmanager.proyecto.dto.ProyectoDto;

import java.util.List;

/**
 * Servicio para gestión de proyectos.
 */
public interface ProyectoService {
    ProyectoDto create(ProyectoCreateDto proyectoCreateDto);
    ProyectoDto findById(Long id);
    List<ProyectoDto> findAll();
    ProyectoDto update(Long id, ProyectoCreateDto proyectoCreateDto);
    void delete(Long id);
}
