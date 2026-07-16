package com.exp.medic.helper.historia;

import com.exp.medic.dto.historial.response.HistoriaClinicaResponseDTO;
import com.exp.medic.model.HistoriaClinica;

public interface IHistoriaMedicaHelper {

    HistoriaClinicaResponseDTO mapearEntidadADto(HistoriaClinica entidad);
}
