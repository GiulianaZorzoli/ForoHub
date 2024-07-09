package com.api.foroHub.domain.usuario;

public record DatosRespuestaUsuario(
        Long id,
        String username
) {
    public DatosRespuestaUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getUsername());
    }
}
