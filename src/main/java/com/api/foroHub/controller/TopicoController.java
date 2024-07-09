package com.api.foroHub.controller;

import com.api.foroHub.domain.topico.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(@RequestBody @Valid DatosCrearTopico datosCrearTopico, UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaTopico respuesta = topicoService.crearTopico(datosCrearTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listarTopicos(@Parameter @PageableDefault(size=3)Pageable pageable){
        Page<DatosListadoTopicos> datosListadoTopicos = topicoService.obtenerTopicos(pageable);
        return ResponseEntity.ok(datosListadoTopicos);
    }

    @GetMapping("/ordenado")
    public ResponseEntity<Page<DatosListadoTopicos>> listarTopicosOrdenAscendente(@Parameter @PageableDefault(size=10, sort = "fechaCreacion", direction = Sort.Direction.ASC)Pageable pageable){
        Page<DatosListadoTopicos> datosListadoTopicos = topicoService.obtenerTopicos(pageable);
        return ResponseEntity.ok(datosListadoTopicos);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<DatosListadoTopicos> buscarTopicoPorNombre(@PathVariable String titulo){
        DatosListadoTopicos respuesta = topicoService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(respuesta);
    }
    @GetMapping("/anio/{anio}")
    public ResponseEntity<Page<DatosListadoTopicos>> buscarTopicosPorAnio(@Parameter @PageableDefault(size=3)Pageable pageable, @PathVariable String anio){
        Page<DatosListadoTopicos> respuesta = topicoService.buscarPorAnio(anio, pageable);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopicos> buscarTopicoPorId(@PathVariable Long id){
        DatosListadoTopicos respuesta = topicoService.buscarPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        DatosRespuestaTopico respuesta = topicoService.actualizarTopico(id, datosActualizarTopico);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicoService.borrarTopico(id);
        return ResponseEntity.noContent().build();
    }
 }
