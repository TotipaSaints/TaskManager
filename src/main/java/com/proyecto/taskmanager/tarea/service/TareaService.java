package com.proyecto.taskmanager.tarea.service;

import com.proyecto.taskmanager.tarea.dto.TareaCreateDto;
import com.proyecto.taskmanager.tarea.dto.TareaDto;
import com.proyecto.taskmanager.tarea.dto.TareaUpdateDto;

import java.util.List;

/**
 * Servicio para operaciones de Tarea.
 */
public interface TareaService {
    TareaDto create(TareaCreateDto tareaCreateDto);
    TareaDto findById(Long id);
    List<TareaDto> findAll();
    TareaDto update(Long id, TareaUpdateDto tareaUpdateDto);
    void delete(Long id);
}
