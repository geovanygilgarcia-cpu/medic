package com.exp.medic.service.catalogs;

import com.exp.medic.dto.catalgos.request.MedicamentoRequestDTO;
import com.exp.medic.dto.catalgos.response.MedicamentoResponseDTO;

import java.util.List;

public interface IMedicamentosService {

    List<MedicamentoResponseDTO> listarTodos();
    List<MedicamentoResponseDTO> buscarPorNombre(String nombre);
    MedicamentoResponseDTO obtenerPorId(Long id);
    MedicamentoResponseDTO crear(MedicamentoRequestDTO dto);
    MedicamentoResponseDTO actualizar(Long id, MedicamentoRequestDTO dto);
    void eliminar(Long id);
}
