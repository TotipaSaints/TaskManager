package com.proyecto.taskmanager.tarea.model;

import com.proyecto.taskmanager.proyecto.model.Proyecto;
import com.proyecto.taskmanager.tarea.enums.Prioridad;
import com.proyecto.taskmanager.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Entidad que representa una tarea dentro de un proyecto.
 */
@Entity
@Table(name = "tarea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    /**
     * Identificador de la tarea.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título corto de la tarea.
     */
    @Column(nullable = false)
    private String titulo;

    /**
     * Descripción detallada (texto libre).
     */
    @Column(columnDefinition = "text")
    private String descripcion;

    /**
     * Prioridad de la tarea.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Prioridad prioridad;

    /**
     * Fecha límite de la tarea.
     */
    @Column(name = "fechavencimiento", nullable = false)
    private Instant fechavencimiento;

    /**
     * Marca si la tarea está completada.
     */
    @Column(nullable = false)
    private Boolean completada;

    /**
     * Proyecto al que pertenece (puede ser null).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyectoid")
    private Proyecto proyecto;

    /**
     * Usuario asignado a la tarea (puede ser null).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignadoa")
    private Usuario asignadoA;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Instant fechaActualizacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = Instant.now();
        this.fechaActualizacion = this.fechaCreacion;
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = Instant.now();
    }
}