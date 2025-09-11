package com.proyecto.taskmanager.proyecto.service.impl;
import com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto;
import com.proyecto.taskmanager.proyecto.dto.ProyectoDto;
import com.proyecto.taskmanager.proyecto.mapper.ProyectoMapper;
import com.proyecto.taskmanager.proyecto.model.Proyecto;
import com.proyecto.taskmanager.proyecto.repository.ProyectoRepository;
import com.proyecto.taskmanager.proyecto.service.ProyectoService;
import com.proyecto.taskmanager.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de proyectos.
 */
@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public ProyectoDto create(ProyectoCreateDto proyectoCreateDto) {
        Proyecto p = ProyectoMapper.toEntity(proyectoCreateDto);
        Proyecto saved = proyectoRepository.save(p);
        return ProyectoMapper.toDto(saved);
    }

    @Override
    public ProyectoDto findById(Long id) {
        Proyecto p = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con id: " + id));
        return ProyectoMapper.toDto(p);
    }

    @Override
    public List<ProyectoDto> findAll() {
        return proyectoRepository.findAll().stream()
                .map(ProyectoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProyectoDto update(Long id, ProyectoCreateDto proyectoCreateDto) {
        Proyecto p = proyectoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado con id: " + id));
        ProyectoMapper.updateFromDto(p, proyectoCreateDto);
        Proyecto saved = proyectoRepository.save(p);
        return ProyectoMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!proyectoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proyecto no encontrado con id: " + id);
        }
        proyectoRepository.deleteById(id);
    }
}
