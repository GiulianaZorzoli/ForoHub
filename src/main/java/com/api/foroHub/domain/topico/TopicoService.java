package com.api.foroHub.domain.topico;

import com.api.foroHub.domain.curso.Curso;
import com.api.foroHub.domain.curso.CursoRepository;
import com.api.foroHub.domain.usuario.Usuario;
import com.api.foroHub.domain.usuario.UsuarioRepository;
import com.api.foroHub.infra.errores.ValidacionDeIntegridad;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public DatosRespuestaTopico crearTopico(DatosCrearTopico datosCrearTopico) {
        if(!usuarioRepository.findById(datosCrearTopico.usuario_id()).isPresent()){
            throw new EntityNotFoundException("No se encontro al usuario.");
        }
        if(!cursoRepository.findByNombre(datosCrearTopico.nombre_curso()).isPresent()){
            throw new EntityNotFoundException("No se encontro el curso.");
        }
        if(topicoRepository.findByTitulo(datosCrearTopico.titulo()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un topico con ese nombre.");
        }
        if(topicoRepository.findByMensaje(datosCrearTopico.mensaje()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un topico con ese mensaje.");
        }
        Usuario autor = usuarioRepository.findById(datosCrearTopico.usuario_id()).get();
        Curso curso = cursoRepository.findByNombre(datosCrearTopico.nombre_curso()).get();
        Topico nuevoTopico = new Topico(datosCrearTopico, autor, curso);

        topicoRepository.save(nuevoTopico);
        return new  DatosRespuestaTopico(nuevoTopico);
    }

    public Page<DatosListadoTopicos> obtenerTopicos(Pageable pageable) {
        Page<Topico> topicos = topicoRepository.findAll(pageable);
        return topicos.map(DatosListadoTopicos::new);
    }

    public DatosListadoTopicos buscarPorTitulo(String titulo) {
        titulo = titulo.replace("+"," ");
        Optional<Topico> topicoOptional = topicoRepository.findByTitulo(titulo);
        if(!topicoOptional.isPresent()){
            throw new EntityNotFoundException("No existe ningun topico con ese titulo.");
        }
        return new DatosListadoTopicos(topicoOptional.get());
    }
    public Page<DatosListadoTopicos> buscarPorAnio(String anio, Pageable pageable) {
        int year = Integer.parseInt(anio);
        Page<Topico> topicos = topicoRepository.findByYear(year, pageable);
        return topicos.map(DatosListadoTopicos::new);
    }

    public DatosListadoTopicos buscarPorId(Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if(!topicoOptional.isPresent()){
            throw new EntityNotFoundException("No existe el topico con ese id.");
        }
        return new DatosListadoTopicos(topicoOptional.get());
    }

    public DatosRespuestaTopico actualizarTopico(Long id, DatosActualizarTopico datosActualizarTopico) {
        Optional<Topico>  topicoOptional = topicoRepository.findById(id);
        if(!topicoOptional.isPresent()){
            throw  new EntityNotFoundException("No existe el topico con ese id.");
        }
        if(topicoRepository.findByTitulo(datosActualizarTopico.titulo()).isPresent()){
            throw  new ValidacionDeIntegridad("Ya existe un topico con ese nombre.");
        }
        if(topicoRepository.findByMensaje(datosActualizarTopico.mensaje()).isPresent()){
            throw  new ValidacionDeIntegridad("Ya existe un topico con ese mensaje.");
        }
        Topico topico = topicoOptional.get();
        topico.actualizarDatos(datosActualizarTopico);
        topicoRepository.save(topico);
        return new DatosRespuestaTopico(topico);
    }

    public void borrarTopico(Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if(!topicoOptional.isPresent()){
            throw new EntityNotFoundException("No existe el topico que deseas borrar.");
        }
        topicoRepository.deleteById(id);
    }


}
