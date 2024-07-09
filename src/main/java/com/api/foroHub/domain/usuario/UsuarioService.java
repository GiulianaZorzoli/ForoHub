package com.api.foroHub.domain.usuario;

import com.api.foroHub.infra.errores.ValidacionDeIntegridad;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //funciona bien?

    public DatosRespuestaUsuario crearUsuario(DatosCrearUsuario datosCrearUsuario) {
        if(usuarioRepository.findByUsername2(datosCrearUsuario.username()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un usuario con ese nombre.");
        }
        if(usuarioRepository.findByUsername2(datosCrearUsuario.email()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un usuario con ese email.");
        }
        String contrasenaEncriptada = passwordEncoder.encode(datosCrearUsuario.contrasena());
        Usuario usuario = new Usuario(datosCrearUsuario,contrasenaEncriptada);
        usuarioRepository.save(usuario);
        return new DatosRespuestaUsuario(usuario);
    }

    public Page<DatosRespuestaUsuario> obtenerUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(DatosRespuestaUsuario::new);
    }

    public DatosRespuestaUsuario buscarPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(!usuarioOptional.isPresent()){
            throw new EntityNotFoundException("No se encontró el usuario.");
        }
        return new DatosRespuestaUsuario(usuarioOptional.get());
    }

    public DatosRespuestaUsuario actualizarUsuario(Long id, DatosActualizarUsuario datosActualizarUsuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(!usuarioOptional.isPresent()){
            throw new EntityNotFoundException("No se encontró el usuario.");
        }
        if(usuarioRepository.findByUsername2(datosActualizarUsuario.username()).isPresent()){
            throw new ValidacionDeIntegridad("Ya existe un usuario con ese nombre.");
        }
        if(usuarioRepository.findByEmail(datosActualizarUsuario.email()).isPresent()){
            throw  new ValidacionDeIntegridad("Ya existe un usuario con ese email.");
        }
        Usuario usuario = usuarioOptional.get();
        usuario.actualizarDatos(datosActualizarUsuario);
        return new DatosRespuestaUsuario(usuario);
    }

    public void borrarUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if(!usuarioOptional.isPresent()){
            throw new EntityNotFoundException("No existe el usuario que deseas borrar.");
        }
        usuarioRepository.deleteById(id);
    }
}
