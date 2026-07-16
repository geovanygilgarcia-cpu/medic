package com.exp.medic.helper.receta;

import com.exp.medic.dto.recetas.response.RecetaResponseDTO;
import com.exp.medic.model.RecetaMedica;

public interface IRecetaMedicaHelper {

    RecetaResponseDTO mapearEntidadADto(RecetaMedica entidad);
}
