package com.exp.medic.service.referencia;

import com.exp.medic.dto.referencia.request.ReferenciaRequestDTO;
import com.exp.medic.dto.referencia.response.ReferenciaResponseDTO;

import java.util.List;

public interface IReferenciaService {

    ReferenciaResponseDTO crear(ReferenciaRequestDTO dto);

    /** Referencias PENDIENTES enviadas al médico actualmente logueado. */
    List<ReferenciaResponseDTO> listarPendientesParaMiUsuario();

    /** Acepta la referencia y transfiere el paciente por completo al médico destino. */
    ReferenciaResponseDTO aceptar(Long id);

    /** Rechaza la referencia; el paciente se queda con el médico que refirió. */
    ReferenciaResponseDTO rechazar(Long id);
}