package com.proyecto.taskmanager.usuario.service.impl;

import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioUpdateDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import com.proyecto.taskmanager.usuario.repository.UsuarioRepository;
import com.proyecto.taskmanager.usuario.service.UsuarioService;
import com.proyecto.taskmanager.util.EncodeText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(UsuarioCreateDto usuarioCreateDto) {

        Usuario usuario = Usuario.fromCreateDto(usuarioCreateDto);
        usuario.setPassword(EncodeText.encriptarText(usuario.getPassword()));

        return this.usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuario() {

        return this.usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuario(Long id) {

        return this.usuarioRepository.findById(id);
    }

    public Boolean eliminarUsuario(Long id) {
        try {
            this.usuarioRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario modificarUsuario(Long id, UsuarioUpdateDto usuarioUpdateDto) {

        Usuario usuario = Usuario.fromUpdateDto(usuarioUpdateDto);

        try {
            Usuario usuarioExiste = this.usuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el id: " + id));

            if (usuario.getNombre() != null) usuarioExiste.setNombre(usuario.getNombre());
            if (usuario.getPassword() != null) usuarioExiste.setPassword(usuario.getPassword());
            if (usuario.getRol() != null) usuarioExiste.setRol(usuario.getRol());
            if (usuario.getActivo() != null) usuarioExiste.setActivo(usuario.getActivo());

            return this.usuarioRepository.save(usuarioExiste);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar " + e);
        }
    }

    public Usuario obtenerUsuarioPorEmail(String email){
        try {
            Usuario usuarioByEmail = this.usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el email : " + email));

            return usuarioByEmail;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario por email");
        }
    }
}
