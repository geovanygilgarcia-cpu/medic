package com.exp.medic.service.catalogs;

import com.exp.medic.dto.catalgos.request.EspecialidadRequestDTO;
import com.exp.medic.dto.catalgos.response.EspecialidadResponseDTO;

import java.util.List;

public interface IEspecialidadService {

    List<EspecialidadResponseDTO> listarTodas();
    EspecialidadResponseDTO obtenerPorId(Long id);
    EspecialidadResponseDTO crear(EspecialidadRequestDTO dto);
    EspecialidadResponseDTO actualizar(Long id, EspecialidadRequestDTO dto);
    void eliminar(Long id);
}
