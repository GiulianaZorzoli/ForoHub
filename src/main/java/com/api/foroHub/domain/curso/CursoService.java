package com.api.foroHub.domain.curso;

import com.api.foroHub.infra.errores.ValidacionDeIntegridad;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public DatosRespuestaCurso crearCurso(DatosCrearCurso datosCrearCurso) {
        if(cursoRepository.findByNombre(datosCrearCurso.nombre()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un curso con ese nombre");
        }
        Curso curso = new Curso(datosCrearCurso);
        cursoRepository.save(curso);
        return new DatosRespuestaCurso(curso);
    }

    public Page<DatosRespuestaCurso> obtenerCursos(Pageable pageable) {
        Page<Curso> cursos = cursoRepository.findAll(pageable);
        return cursos.map(DatosRespuestaCurso::new);
    }

    public DatosRespuestaCurso buscarPorId(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if(!cursoOptional.isPresent()){
            throw new EntityNotFoundException("No se encontró el curso.");
        }
        return new DatosRespuestaCurso(cursoOptional.get());
    }

    public DatosRespuestaCurso actualizarUsuario(Long id, DatosActualizarCurso datosActualizarCurso) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if(!cursoOptional.isPresent()){
            throw new EntityNotFoundException("No se encontró el curso que deseas actualizar.");
        }
        if(cursoRepository.findByNombre(datosActualizarCurso.nombre()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un curso con ese nombre.");
        }
        Curso curso = cursoOptional.get();
        curso.actualizarDatos(datosActualizarCurso);
        cursoRepository.save(curso);
        return new DatosRespuestaCurso(curso);
    }

    public void borrarCurso(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if(!cursoOptional.isPresent()){
            throw new EntityNotFoundException("No se encontró el curso que deseas borrar.");
        }
        cursoRepository.deleteById(id);
    }
}

