package com.exp.medic.dto.historial.response;

import com.exp.medic.dto.historial.request.*;

import java.time.LocalDateTime;

public record HistoriaClinicaResponseDTO(
        Long id,
        Long pacienteId,
        PatientInformationDTO patientInformation,
        MedicalHistoryDTO medicalHistory,
        FamilyMedicalHistoryDTO familyMedicalHistory,
        ReasonForVisitDTO reasonForVisit,
        DoctorNotesDTO doctorNotes,
        LocalDateTime createdAt
) {
}