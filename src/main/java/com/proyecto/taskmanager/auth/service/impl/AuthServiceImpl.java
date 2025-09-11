package com.proyecto.taskmanager.auth.service.impl;

import com.proyecto.taskmanager.auth.service.AuthService;
import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import com.proyecto.taskmanager.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioService usuarioService;

    public Usuario registrarUsuario(UsuarioCreateDto usuarioCreateDto) {
        return usuarioService.crearUsuario(usuarioCreateDto);
    }


}
