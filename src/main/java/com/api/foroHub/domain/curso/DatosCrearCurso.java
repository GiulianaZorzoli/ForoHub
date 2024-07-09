package com.api.foroHub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCrearCurso(
        @NotBlank
        String nombre,
        @NotNull
        Categoria categoria
) {
}
