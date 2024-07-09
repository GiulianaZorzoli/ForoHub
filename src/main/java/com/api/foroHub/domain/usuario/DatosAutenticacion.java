package com.api.foroHub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacion(
        @NotBlank
        String username,
        @NotBlank
        String contrasena
) {
}
