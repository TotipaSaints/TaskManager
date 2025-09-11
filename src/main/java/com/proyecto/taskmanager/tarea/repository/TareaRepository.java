package com.proyecto.taskmanager.tarea.repository;

import com.proyecto.taskmanager.tarea.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para Tareas.
 */
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByProyectoId(Long proyectoId);
    List<Tarea> findByAsignadoAId(Long usuarioId);
}
