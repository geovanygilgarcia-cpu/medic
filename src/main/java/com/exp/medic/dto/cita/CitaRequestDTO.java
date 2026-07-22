package com.exp.medic.dto.cita;

import com.exp.medic.model.EstadoCita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

// Espeja com.exp.medic.dto.cita.CitaRequestDTO — igual patrón que ReferenciaRequestDTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaRequestDTO {

    @NotBlank(message = "El médico es obligatorio")
    private String medicoId;

    @NotBlank(message = "El nombre del médico es obligatorio")
    private String medicoNombre;

    private Long pacienteId;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(max = 200)
    private String pacienteNombre;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotBlank(message = "El tipo de cita es obligatorio")
    private String tipo;

    @NotNull(message = "El estado es obligatorio")
    private EstadoCita estado;

    @Size(max = 1000)
    private String notas;
}
