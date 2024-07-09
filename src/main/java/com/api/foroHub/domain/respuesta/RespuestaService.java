package com.api.foroHub.domain.respuesta;

import com.api.foroHub.domain.topico.DatosListadoTopicos;
import com.api.foroHub.domain.topico.Topico;
import com.api.foroHub.domain.topico.TopicoRepository;
import com.api.foroHub.domain.usuario.Usuario;
import com.api.foroHub.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public DatosRespuesta crearRespuesta(DatosRespuesta datosCrearRespuesta, Long id) {
        if(!topicoRepository.findById(id).isPresent()){
            throw new EntityNotFoundException("No existe el topico buscado.");
        }
        if(!usuarioRepository.findById(datosCrearRespuesta.usuario_id()).isPresent()){
            throw new EntityNotFoundException("No existe el usuario ingresado.");
        }
        Usuario autor = usuarioRepository.findById(datosCrearRespuesta.usuario_id()).get();
        Topico topico = topicoRepository.findById(id).get();
        Respuesta respuesta = new Respuesta(datosCrearRespuesta, autor, topico);

        topico.getRespuestas().add(respuesta);

        respuestaRepository.save(respuesta);
        topicoRepository.save(topico);

        return new DatosRespuesta(respuesta);

    }

    public Page<DatosRespuesta> obtenerRespuestas(Long id, Pageable pageable) {
        if(!topicoRepository.findById(id).isPresent()){
            throw new EntityNotFoundException("No existe el topico buscado.");
        }
        Page<Respuesta> respuestas = respuestaRepository.findByTopico(topicoRepository.findById(id).get(),pageable);
        return respuestas.map(DatosRespuesta::new);
    }

    public DatosRespuesta obtenerRespuestaPorId(Long id, Long idRespuesta) {
        if(!topicoRepository.findById(id).isPresent()){
            throw new EntityNotFoundException("No existe el topico buscado.");
        }
        if(!respuestaRepository.findById(idRespuesta).isPresent()){
            throw new EntityNotFoundException("No existe la respuesta buscada.");
        }
        if(!topicoRepository.findById(id).get().getRespuestas().contains(respuestaRepository.findById(idRespuesta).get())){
            throw new IllegalArgumentException("La respuesta no pertenece al tópico especificado.");
        }
        Respuesta respuesta = respuestaRepository.findById(idRespuesta).get();
        return new DatosRespuesta(respuesta);

    }

    public DatosRespuesta actualizarRespuestaPorId(Long id, Long idRespuesta, DatosActualizarRespuesta datosActualizarRespuesta) {
        if(!topicoRepository.findById(id).isPresent()){
            throw new EntityNotFoundException("No existe el topico buscado.");
        }
        if(!respuestaRepository.findById(idRespuesta).isPresent()){
            throw new EntityNotFoundException("No existe la respuesta buscada.");
        }
        if(!topicoRepository.findById(id).get().getRespuestas().contains(respuestaRepository.findById(idRespuesta).get())){
            throw new IllegalArgumentException("La respuesta no pertenece al tópico especificado.");
        }
        if(!topicoRepository.findById(id).get().getRespuestas().contains(respuestaRepository.findById(idRespuesta).get())){
            throw new IllegalArgumentException("La respuesta no pertenece al tópico especificado.");
        }
        Respuesta respuesta = respuestaRepository.findById(idRespuesta).get();
        respuesta.actualizar(datosActualizarRespuesta);
        respuestaRepository.save(respuesta);
        return new DatosRespuesta(respuesta);

    }

    public void borrarRespuestaDeTopico(Long id, Long idRespuesta) {
        if(!topicoRepository.findById(id).isPresent()){
            throw new EntityNotFoundException("No existe el topico buscado.");
        }
        if(!respuestaRepository.findById(idRespuesta).isPresent()){
            throw new EntityNotFoundException("No existe la respuesta que quieres borrar.");
        }
        if(!topicoRepository.findById(id).get().getRespuestas().contains(respuestaRepository.findById(idRespuesta).get())){
            throw new IllegalArgumentException("La respuesta no pertenece al tópico especificado.");
        }

        Respuesta respuesta = respuestaRepository.findById(idRespuesta).get();
        Topico topico = topicoRepository.findById(id).get();
        topico.getRespuestas().remove(respuesta);
        topicoRepository.save(topico);
        respuestaRepository.deleteById(idRespuesta);

    }
}
