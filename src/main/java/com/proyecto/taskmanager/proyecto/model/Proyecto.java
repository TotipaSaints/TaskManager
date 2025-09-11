package com.proyecto.taskmanager.proyecto.model;

import com.proyecto.taskmanager.proyecto.enums.EstadoProyecto;
import com.proyecto.taskmanager.tarea.model.Tarea;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

/**
 * Entidad que representa un proyecto.
 */
@Entity
@Table(name = "proyecto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proyecto {

    /**
     * Identificador único del proyecto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del proyecto.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Descripción opcional del proyecto.
     */
    @Column(columnDefinition = "text")
    private String descripcion;

    /**
     * Fecha y hora de inicio del proyecto.
     */
    @Column(name = "fechainicio", nullable = false)
    private Instant fechaInicio;

    /**
     * Fecha y hora de finalización (opcional).
     */
    @Column(name = "fechafin")
    private Instant fechaFin;

    /**
     * Estado del proyecto: ACTIVO o INACTIVO.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProyecto estado;

    /**
     * Lista de tareas asociadas al proyecto.
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas;

    /**
     * Fecha de creación (se setea automáticamente al persistir).
     */
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    /**
     * Fecha de última actualización (se actualiza en cada modificación).
     */
    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    /**
     * Hooks de JPA para setear timestamps automáticamente.
     */
    @PrePersist
    public void prePersist() {
        fechaCreacion = Instant.now();
        fechaActualizacion = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        fechaActualizacion = Instant.now();
    }
}
