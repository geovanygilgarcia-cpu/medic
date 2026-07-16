package com.exp.medic.service.historia;

import com.exp.medic.dto.historial.request.PatientIntakeFormDTO;
import com.exp.medic.dto.historial.response.HistoriaClinicaResponseDTO;

import java.util.List;

public interface IHistoriaClinicaCrudService {

    HistoriaClinicaResponseDTO crear(PatientIntakeFormDTO dto);

    HistoriaClinicaResponseDTO actualizar(Long id, PatientIntakeFormDTO dto);

    void eliminar(Long id);

    List<HistoriaClinicaResponseDTO> listarTodas();

    HistoriaClinicaResponseDTO obtenerPorId(Long id);

    List<HistoriaClinicaResponseDTO> listarPorPaciente(Long pacienteId);
}