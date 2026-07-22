package com.exp.medic.dto.cita.response;

import com.exp.medic.model.EstadoCita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

// Espeja com.exp.medic.dto.cita.response.CitaResponseDTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaResponseDTO {

    private Long id;
    private String medicoId;
    private String medicoNombre;
    private Long pacienteId;
    private String pacienteNombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String tipo;
    private EstadoCita estado;
    private String notas;
    private String googleEventId;
}
