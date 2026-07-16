package com.exp.medic.service.paciente;

import com.exp.medic.dto.paciente.request.PacienteDTO;
import com.exp.medic.dto.paciente.response.PacienteResponseDTO;

import java.util.List;

public interface IPacienteCrudService {

    PacienteResponseDTO crear(PacienteDTO dto);

    PacienteResponseDTO actualizar(Long id, PacienteDTO dto);

    void eliminar(Long id);

    List<PacienteResponseDTO> listarTodos();

    PacienteResponseDTO obtenerPorId(Long id);
}
