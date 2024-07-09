package com.api.foroHub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long usuario_id,
        @NotNull
        Long topico_id
) {
    public DatosRespuesta(Respuesta respuesta) {
        this(respuesta.getMensaje(),respuesta.getAutor().getId(),respuesta.getTopico().getId());
    }
}
