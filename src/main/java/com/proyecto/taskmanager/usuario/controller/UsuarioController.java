package com.proyecto.taskmanager.usuario.controller;

import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import com.proyecto.taskmanager.usuario.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return this.usuarioServiceImpl.listarUsuario();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Usuario> buscar(@PathVariable Long id) {
        return this.usuarioServiceImpl.buscarUsuario(id);
    }

    @DeleteMapping("/eliminar/{id}")
    public Boolean eliminar(@PathVariable Long id) {
        return this.usuarioServiceImpl.eliminarUsuario(id);
    }

    @PutMapping("/{id}")
    public Usuario modificar(@PathVariable Long id, @RequestBody UsuarioUpdateDto usuarioUpdateDto) {
        return this.usuarioServiceImpl.modificarUsuario(id, usuarioUpdateDto);
    }

}
