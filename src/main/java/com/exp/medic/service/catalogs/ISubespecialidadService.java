package com.exp.medic.service.catalogs;

import com.exp.medic.dto.catalgos.request.SubespecialidadRequestDTO;
import com.exp.medic.dto.catalgos.response.SubespecialidadResponseDTO;

import java.util.List;

public interface ISubespecialidadService {

    List<SubespecialidadResponseDTO> listarTodas(Long especialidadId);
    SubespecialidadResponseDTO obtenerPorId(Long id);
    SubespecialidadResponseDTO crear(SubespecialidadRequestDTO dto);
    SubespecialidadResponseDTO actualizar(Long id, SubespecialidadRequestDTO dto);
    void eliminar(Long id);
}
