package com.proyecto.taskmanager.usuario.service;

import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;

public interface UsuarioService {

    Usuario crearUsuario(UsuarioCreateDto usuarioCreateDto);

    Usuario modificarUsuario(Long id, UsuarioUpdateDto usuarioUpdateDto);

    Usuario obtenerUsuarioPorEmail(String email);
}
