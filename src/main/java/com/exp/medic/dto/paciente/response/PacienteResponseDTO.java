package com.exp.medic.dto.paciente.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteResponseDTO(
        Long id,
        String expediente,
        String nombreCompleto,
        String iniciales,
        Integer edad,
        String telefono,
        LocalDate fechaNacimiento,
        String email,
        String genero,
        String contactoEmergencia,
        String medicoId,
        String medicoNombre,
        LocalDateTime createdAt
) {
}
