package com.exp.medic.service.receta;

import com.exp.medic.dto.recetas.request.RecetaDTO;
import com.exp.medic.dto.recetas.response.RecetaResponseDTO;

import java.util.List;

public interface IRecetaMedicaCrudService {

    RecetaResponseDTO crear(RecetaDTO dto);

    RecetaResponseDTO actualizar(Long id, RecetaDTO dto);

    void eliminar(Long id);

    List<RecetaResponseDTO> listarTodas();

    RecetaResponseDTO obtenerPorId(Long id);

    List<RecetaResponseDTO> listarPorPaciente(Long pacienteId);
}