package com.api.foroHub.controller;

import com.api.foroHub.domain.curso.CursoService;
import com.api.foroHub.domain.curso.DatosActualizarCurso;
import com.api.foroHub.domain.curso.DatosCrearCurso;
import com.api.foroHub.domain.curso.DatosRespuestaCurso;
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
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> crearCurso(@RequestBody @Valid DatosCrearCurso datosCrearCurso, UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaCurso respuesta = cursoService.crearCurso(datosCrearCurso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaCurso>> listarCursos(@Parameter @PageableDefault(size=3) Pageable pageable){
        Page<DatosRespuestaCurso> datosCurso = cursoService.obtenerCursos(pageable);
        return ResponseEntity.ok(datosCurso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> buscarCursoPorId(@PathVariable Long id){
        DatosRespuestaCurso respuesta = cursoService.buscarPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> actualizarCurso(@PathVariable Long id, @RequestBody @Valid DatosActualizarCurso datosActualizarCurso){
        DatosRespuestaCurso respuesta = cursoService.actualizarUsuario(id, datosActualizarCurso);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarCurso(@PathVariable Long id){
        cursoService.borrarCurso(id);
        return ResponseEntity.noContent().build();
    }

}
