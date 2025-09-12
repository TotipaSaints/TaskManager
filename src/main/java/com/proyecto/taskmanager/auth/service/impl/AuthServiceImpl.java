package com.proyecto.taskmanager.auth.service.impl;

import com.proyecto.taskmanager.auth.dto.InicioSesionDto;
import com.proyecto.taskmanager.auth.service.AuthService;
import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import com.proyecto.taskmanager.usuario.service.UsuarioService;
import com.proyecto.taskmanager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    //@Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Usuario registrarUsuario(UsuarioCreateDto usuarioCreateDto) {
        return usuarioService.crearUsuario(usuarioCreateDto);
    }

    @Override
    public void iniciarSesion(InicioSesionDto inicioSesionDto) {

        try {
            Usuario usuarioExiste = usuarioService.obtenerUsuarioPorEmail(inicioSesionDto.getEmail());

            //if (bCryptPasswordEncoder.matches(inicioSesionDto.getPassword(), usuarioExiste.getPassword())) {
                System.out.println(jwtUtil.generateToken(usuarioExiste));
            //}

        } catch (Exception e) {
            throw new RuntimeException("Error al iniciar sesion");
        }

    }


}
