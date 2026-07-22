package com.exp.medic.service.cita;

import com.exp.medic.dto.cita.CitaRequestDTO;
import com.exp.medic.dto.cita.response.CitaResponseDTO;
import com.exp.medic.model.EstadoCita;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICitaService {

    List<CitaResponseDTO> listarPorMedico(String medicoId, LocalDate desde, LocalDate hasta);

    CitaResponseDTO obtenerPorId(Long id);

    CitaResponseDTO crear(CitaRequestDTO request);

    CitaResponseDTO actualizar(Long id, CitaRequestDTO request);

    CitaResponseDTO cambiarEstado(Long id, EstadoCita estado);

    void eliminar(Long id);

    Optional<CitaResponseDTO> obtenerProximaCita(String medicoId);
}