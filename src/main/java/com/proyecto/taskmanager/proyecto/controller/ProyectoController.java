package com.proyecto.taskmanager.proyecto.controller;
import com.proyecto.taskmanager.proyecto.dto.ProyectoCreateDto;
import com.proyecto.taskmanager.proyecto.dto.ProyectoDto;
import com.proyecto.taskmanager.proyecto.service.ProyectoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para proyectos.
 */
@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    /**
     * Crea un proyecto.
     */
    @PostMapping
    public ResponseEntity<ProyectoDto> create(@Valid @RequestBody ProyectoCreateDto proyectoCreateDto) {
        ProyectoDto created = proyectoService.create(proyectoCreateDto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Lista proyectos.
     */
    @GetMapping
    public ResponseEntity<List<ProyectoDto>> findAll() {
        return ResponseEntity.ok(proyectoService.findAll());
    }

    /**
     * Obtiene proyecto por id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProyectoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(proyectoService.findById(id));
    }

    /**
     * Actualiza un proyecto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProyectoDto> update(@PathVariable Long id, @Valid @RequestBody ProyectoCreateDto proyectoCreateDto) {
        return ResponseEntity.ok(proyectoService.update(id, proyectoCreateDto));
    }

    /**
     * Elimina un proyecto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proyectoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
