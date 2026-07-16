package com.exp.medic.helper.paciente;

import com.exp.medic.dto.paciente.response.PacienteResponseDTO;
import com.exp.medic.model.Paciente;

public interface IPacienteHelper {
    PacienteResponseDTO mapearEntidadADto(Paciente entidad);
}
