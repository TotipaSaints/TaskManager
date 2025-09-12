----------------------------------------------------------------
-- Tabla proyecto
----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.proyecto (
  id     int8 GENERATED ALWAYS AS IDENTITY
            ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE ) NOT NULL,
  nombre text NOT NULL,
  descripcion text NULL,
  fechainicio date NOT NULL,
  fechafin date NULL,
  estado text NOT NULL DEFAULT 'ACTIVO',
  CONSTRAINT proyecto_pkey PRIMARY KEY (id),
  CONSTRAINT proyecto_estado_check CHECK (estado = ANY (ARRAY['ACTIVO','INACTIVO']))
);

CREATE INDEX IF NOT EXISTS idx_proyecto_estado ON public.proyecto USING btree (estado);

----------------------------------------------------------------
-- Tabla usuarios
----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.usuarios (
  id int8 GENERATED ALWAYS AS IDENTITY
       ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE ) NOT NULL,
  nombre varchar(100) NOT NULL,
  email varchar(100) NOT NULL,
  "password" varchar(255) NOT NULL,
  rol text NOT NULL DEFAULT 'USER',
  activo boolean NOT NULL DEFAULT true,
  fecha_creacion timestamptz NOT NULL DEFAULT now(),
  fecha_actualizacion timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT usuarios_pkey PRIMARY KEY (id),
  CONSTRAINT usuarios_email_key UNIQUE (email),
  CONSTRAINT usuarios_rol_check CHECK (rol = ANY (ARRAY['ADMIN','USER']))
);

-- índice para búsqueda case-insensitive por email
CREATE UNIQUE INDEX IF NOT EXISTS ux_usuarios_email_lower ON public.usuarios USING btree (lower(email));

----------------------------------------------------------------
-- Tabla tarea
----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS public.tarea (
  id int8 GENERATED ALWAYS AS IDENTITY
      ( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE ) NOT NULL,
  titulo text NOT NULL,
  descripcion text NULL,
  prioridad text NOT NULL,
  fechavencimiento date NOT NULL,
  completada boolean NOT NULL DEFAULT false,
  proyectoid int8 NULL,
  asignadoa int8 NULL,
  fecha_creacion timestamptz NOT NULL DEFAULT now(),
  fecha_actualizacion timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT tarea_pkey PRIMARY KEY (id),
  CONSTRAINT tarea_prioridad_check CHECK (prioridad = ANY (ARRAY['ALTA','MEDIA','BAJA'])),
  CONSTRAINT tarea_asignadoa_fkey FOREIGN KEY (asignadoa) REFERENCES public.usuarios(id) ON DELETE SET NULL,
  CONSTRAINT tarea_proyectoid_fkey FOREIGN KEY (proyectoid) REFERENCES public.proyecto(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_tarea_asignadoa ON public.tarea USING btree (asignadoa);
CREATE INDEX IF NOT EXISTS idx_tarea_fechavencimiento ON public.tarea USING btree (fechavencimiento);
CREATE INDEX IF NOT EXISTS idx_tarea_prioridad ON public.tarea USING btree (prioridad);
CREATE INDEX IF NOT EXISTS idx_tarea_proyectoid ON public.tarea USING btree (proyectoid);

-- índices parciales para tareas pendientes (mejoran consultas estilo "tareas no completadas ordenadas por vencimiento")
CREATE INDEX IF NOT EXISTS idx_tarea_proyecto_pendientes
  ON public.tarea USING btree (proyectoid, fechavencimiento)
  WHERE (completada = false);

CREATE INDEX IF NOT EXISTS idx_tarea_usuario_pendientes
  ON public.tarea USING btree (asignadoa, fechavencimiento)
  WHERE (completada = false);