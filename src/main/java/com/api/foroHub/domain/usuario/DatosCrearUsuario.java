package com.api.foroHub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosCrearUsuario(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String contrasena
) {
}
