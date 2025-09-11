package com.proyecto.taskmanager.proyecto.repository;

import com.proyecto.taskmanager.proyecto.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para Proyectos.
 */
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}
