package com.api.foroHub.controller;

import com.api.foroHub.domain.respuesta.DatosActualizarRespuesta;
import com.api.foroHub.domain.respuesta.DatosRespuesta;
import com.api.foroHub.domain.respuesta.RespuestaService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("topicos/{id}/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuesta> crearRespuesta(@RequestBody @Valid DatosRespuesta datosCrearRespuesta, @PathVariable Long id){
        DatosRespuesta respuesta = respuestaService.crearRespuesta(datosCrearRespuesta, id);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuesta>> obtenerRespuestasDeTopico(@PathVariable Long id, @Parameter @PageableDefault(size=3) Pageable pageable){
        Page<DatosRespuesta> datosRespuestas = respuestaService.obtenerRespuestas(id, pageable);
        return ResponseEntity.ok(datosRespuestas);

    }

    @GetMapping("/{id_respuesta}")
    public ResponseEntity<DatosRespuesta> obtenerRespuestasDeTopico(@PathVariable Long id,@PathVariable Long id_respuesta){
        DatosRespuesta datosRespuestas = respuestaService.obtenerRespuestaPorId(id,id_respuesta);
        return ResponseEntity.ok(datosRespuestas);

    }

    @PutMapping("/{id_respuesta}")
    @Transactional
    public ResponseEntity<DatosRespuesta> actualizarRespuestaDeTopico(@PathVariable Long id, @PathVariable Long id_respuesta, @RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        DatosRespuesta respuesta = respuestaService.actualizarRespuestaPorId(id, id_respuesta, datosActualizarRespuesta);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id_respuesta}")
    @Transactional
    public ResponseEntity borrarRespuestaDeTopico(@PathVariable Long id, @PathVariable Long id_respuesta){
        respuestaService.borrarRespuestaDeTopico(id, id_respuesta);
        return ResponseEntity.noContent().build();
    }

}
