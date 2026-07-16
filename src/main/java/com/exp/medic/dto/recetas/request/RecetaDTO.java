package com.exp.medic.dto.recetas.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecetaDTO(

        @NotNull(message = "El paciente es obligatorio")
        Long pacienteId,

        String folio,

        @NotBlank(message = "El nombre del paciente es obligatorio")
        String paciente,

        String edad,

        @Valid
        FechaDTO fecha,

        @Valid
        SignosVitalesDTO signosVitales,

        String idx,

        String diagnosticoTratamiento,

        String proximaCita,

        String firma
) {}