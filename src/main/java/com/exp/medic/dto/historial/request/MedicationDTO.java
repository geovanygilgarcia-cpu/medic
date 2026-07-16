package com.exp.medic.dto.historial.request;

import jakarta.validation.constraints.NotNull;

public record MedicationDTO(

        @NotNull(message = "Debe indicar si toma algún medicamento")
        Boolean active,

        String medicationName
) {
}