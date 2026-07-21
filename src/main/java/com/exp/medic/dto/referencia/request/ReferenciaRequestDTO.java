package com.exp.medic.dto.referencia.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReferenciaRequestDTO(

        @NotNull(message = "El paciente es requerido")
        Long pacienteId,

        @NotBlank(message = "El médico destino es requerido")
        String medicoDestinoId,

        // El frontend ya tiene el nombre completo del médico destino (viene
        // del catálogo de usuarios), así que lo mandamos junto con el id
        // para denormalizarlo aquí, igual que se hace con medicoNombre en
        // Paciente. No hay forma de resolverlo del lado del auth-service
        // sin una llamada extra entre microservicios.
        @NotBlank(message = "El nombre del médico destino es requerido")
        String medicoDestinoNombre,

        String motivo
) {
}