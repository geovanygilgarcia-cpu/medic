package com.exp.medic.dto.recetas.response;

import com.exp.medic.dto.recetas.request.FechaDTO;
import com.exp.medic.dto.recetas.request.SignosVitalesDTO;

import java.time.LocalDateTime;

public record RecetaResponseDTO(
        Long id,
        Long pacienteId,
        String folio,
        String paciente,
        String edad,
        FechaDTO fecha,
        SignosVitalesDTO signosVitales,
        String idx,
        String diagnosticoTratamiento,
        String proximaCita,
        String firma,
        LocalDateTime createdAt
) {
}