package com.api.foroHub.controller;

import com.api.foroHub.domain.usuario.DatosActualizarUsuario;
import com.api.foroHub.domain.usuario.DatosCrearUsuario;
import com.api.foroHub.domain.usuario.DatosRespuestaUsuario;
import com.api.foroHub.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> crearUsuario(@RequestBody @Valid DatosCrearUsuario datosCrearUsuario, UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaUsuario respuesta = usuarioService.crearUsuario(datosCrearUsuario);
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaUsuario>> listarUsuarios(@Parameter @PageableDefault(size=3) Pageable pageable){
        Page<DatosRespuestaUsuario> datosUsuarios = usuarioService.obtenerUsuarios(pageable);
        return ResponseEntity.ok(datosUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaUsuario> buscarUsuarioPorId(@PathVariable Long id){
        DatosRespuestaUsuario respuesta = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        DatosRespuestaUsuario respuesta = usuarioService.actualizarUsuario(id, datosActualizarUsuario);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id){
        usuarioService.borrarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
