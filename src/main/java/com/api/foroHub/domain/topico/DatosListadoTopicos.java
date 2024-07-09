package com.api.foroHub.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopicos(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Estado estado,
        String usuario,
        String curso
){
    public DatosListadoTopicos(Topico topico){
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor().getUsername(),
                topico.getCurso().getNombre()
        );
    }
}
