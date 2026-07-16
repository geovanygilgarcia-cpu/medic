package com.exp.medic.helper.historia.impl;

import com.exp.medic.dto.historial.response.HistoriaClinicaResponseDTO;
import com.exp.medic.helper.historia.IHistoriaMedicaHelper;
import com.exp.medic.model.HistoriaClinica;
import org.springframework.stereotype.Component;

@Component
public class HistoriaMedicaHelperImpl implements IHistoriaMedicaHelper {

    @Override
    public HistoriaClinicaResponseDTO mapearEntidadADto(HistoriaClinica entidad) {
        return new HistoriaClinicaResponseDTO(
                entidad.getId(),
                entidad.getPacienteId(),
                entidad.getPatientInformation(),
                entidad.getMedicalHistory(),
                entidad.getFamilyMedicalHistory(),
                entidad.getReasonForVisit(),
                entidad.getDoctorNotes(),
                entidad.getCreatedAt()
        );
    }
}