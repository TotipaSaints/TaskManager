package com.proyecto.taskmanager.tarea.service.impl;

import com.proyecto.taskmanager.proyecto.model.Proyecto;
import com.proyecto.taskmanager.proyecto.repository.ProyectoRepository;
import com.proyecto.taskmanager.tarea.dto.TareaCreateDto;
import com.proyecto.taskmanager.tarea.dto.TareaDto;
import com.proyecto.taskmanager.tarea.dto.TareaUpdateDto;
import com.proyecto.taskmanager.tarea.mapper.TareaMapper;
import com.proyecto.taskmanager.tarea.model.Tarea;
import com.proyecto.taskmanager.tarea.repository.TareaRepository;
import com.proyecto.taskmanager.tarea.service.TareaService;
import com.proyecto.taskmanager.usuario.model.Usuario;
import com.proyecto.taskmanager.usuario.repository.UsuarioRepository;
import com.proyecto.taskmanager.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de tareas.
 */
@Service
public class TareaServiceImpl implements TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public TareaDto create(TareaCreateDto tareaCreateDto) {
        Proyecto proyecto = null;
        if (tareaCreateDto.getProyectoId() != null) {
            proyecto = proyectoRepository.findById(tareaCreateDto.getProyectoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con id: " + tareaCreateDto.getProyectoId()));
        }

        Usuario asignado = null;
        if (tareaCreateDto.getAsignadoAId() != null) {
            asignado = usuarioRepository.findById(tareaCreateDto.getAsignadoAId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + tareaCreateDto.getAsignadoAId()));
        }

        Tarea tarea = TareaMapper.toEntity(tareaCreateDto, proyecto, asignado);
        Tarea saved = tareaRepository.save(tarea);
        return TareaMapper.fromEntity(saved);
    }

    @Override
    public TareaDto findById(Long id) {
        Tarea t = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
        return TareaMapper.fromEntity(t);
    }

    @Override
    public List<TareaDto> findAll() {
        return tareaRepository.findAll().stream()
                .map(TareaMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TareaDto update(Long id, TareaUpdateDto dto) {
        Tarea t = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));

        if (dto.getTitulo() != null) t.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null) t.setDescripcion(dto.getDescripcion());
        if (dto.getPrioridad() != null) t.setPrioridad(dto.getPrioridad());
        if (dto.getFechavencimiento() != null) t.setFechavencimiento(dto.getFechavencimiento());
        if (dto.getCompletada() != null) t.setCompletada(dto.getCompletada());

        if (dto.getProyectoId() != null) {
            Proyecto p = proyectoRepository.findById(dto.getProyectoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con id: " + dto.getProyectoId()));
            t.setProyecto(p);
        }

        if (dto.getAsignadoAId() != null) {
            Usuario u = usuarioRepository.findById(dto.getAsignadoAId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + dto.getAsignadoAId()));
            t.setAsignadoA(u);
        }

        Tarea saved = tareaRepository.save(t);
        return TareaMapper.fromEntity(saved);
    }

    @Override
    public void delete(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarea no encontrada con id: " + id);
        }
        tareaRepository.deleteById(id);
    }
}
