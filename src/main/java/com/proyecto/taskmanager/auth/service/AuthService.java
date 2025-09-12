package com.proyecto.taskmanager.auth.service;

import com.proyecto.taskmanager.auth.dto.InicioSesionDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;

public interface AuthService {

    Usuario registrarUsuario(UsuarioCreateDto usuarioCreateDto);

    void iniciarSesion(InicioSesionDto inicioSesionDto);
}
