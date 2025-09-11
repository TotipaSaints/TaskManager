package com.proyecto.taskmanager.auth.controller;

import com.proyecto.taskmanager.auth.service.impl.AuthServiceImpl;
import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @PostMapping("/registrarse")
    public Usuario registrarUsuario(@RequestBody UsuarioCreateDto usuarioCreateDto){
        return authServiceImpl.registrarUsuario(usuarioCreateDto);
    }
}
