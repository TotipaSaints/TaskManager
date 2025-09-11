package com.proyecto.taskmanager.tarea.controller;
import com.proyecto.taskmanager.tarea.dto.TareaCreateDto;
import com.proyecto.taskmanager.tarea.dto.TareaDto;
import com.proyecto.taskmanager.tarea.dto.TareaUpdateDto;
import com.proyecto.taskmanager.tarea.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints REST para tareas.
 */
@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    /**
     * Crea una tarea.
     */
    @PostMapping
    public ResponseEntity<TareaDto> create(@Valid @RequestBody TareaCreateDto tareaCreateDto) {
        TareaDto created = tareaService.create(tareaCreateDto);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Lista todas las tareas.
     */
    @GetMapping
    public ResponseEntity<List<TareaDto>> findAll() {
        return ResponseEntity.ok(tareaService.findAll());
    }

    /**
     * Obtiene tarea por id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TareaDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.findById(id));
    }

    /**
     * Actualiza una tarea parcialmente.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TareaDto> update(@PathVariable Long id, @Valid @RequestBody TareaUpdateDto tareaUpdateDto) {
        return ResponseEntity.ok(tareaService.update(id, tareaUpdateDto));
    }

    /**
     * Elimina una tarea.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tareaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
