package com.exp.medic.dto.referencia.response;

import com.exp.medic.model.EstadoReferencia;

import java.time.LocalDateTime;

public record ReferenciaResponseDTO(
        Long id,
        Long pacienteId,
        String pacienteNombre,
        String medicoOrigenId,
        String medicoOrigenNombre,
        String medicoDestinoId,
        String medicoDestinoNombre,
        String motivo,
        EstadoReferencia estado,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaResolucion
) {
}