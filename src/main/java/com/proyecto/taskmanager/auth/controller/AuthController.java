package com.proyecto.taskmanager.auth.controller;

import com.proyecto.taskmanager.auth.dto.InicioSesionDto;
import com.proyecto.taskmanager.auth.service.AuthService;
import com.proyecto.taskmanager.usuario.dto.UsuarioCreateDto;
import com.proyecto.taskmanager.usuario.dto.UsuarioDto;
import com.proyecto.taskmanager.usuario.model.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Crear usuario (ADMIN)",
            description = "Crea un nuevo usuario. Ejemplos usan nombres Pokémon de la Gen I.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO con datos para crear usuario (password es write-only).",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioCreateDto.class),
                            examples = {
                                    @ExampleObject(name = "pikachu-crear", value = """
                        {
                          "nombre": "Pikachu",
                          "email": "pikachu@ejemplo.com",
                          "password": "VoltTackle123",
                          "rol": "USER"
                        }
                        """),
                                    @ExampleObject(name = "bulbasaur-crear", value = """
                        {
                          "nombre": "Bulbasaur",
                          "email": "bulbasaur@ejemplo.com",
                          "password": "VineWhip456",
                          "rol": "USER"
                        }
                        """)
                            }
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDto.class)),
            headers = @Header(name = "Location", description = "URL del recurso creado", schema = @Schema(type = "string"))
    )
    @ApiResponse(responseCode = "400", description = "Datos inválidos (validación)", content = @Content)
    @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    @PostMapping("/registrarse")
    public Usuario registrarUsuario(@RequestBody UsuarioCreateDto usuarioCreateDto){
        return authService.registrarUsuario(usuarioCreateDto);
    }

    @PostMapping
    public void iniciarSesion(@RequestBody InicioSesionDto inicioSesionDto){
        authService.iniciarSesion(inicioSesionDto);
    }

}
