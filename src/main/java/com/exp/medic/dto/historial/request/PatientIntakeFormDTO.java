package com.exp.medic.dto.historial.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PatientIntakeFormDTO(

        @NotNull(message = "El paciente es obligatorio")
        Long pacienteId,

        @NotNull @Valid
        PatientInformationDTO patientInformation,

        @NotNull @Valid
        MedicalHistoryDTO medicalHistory,

        @NotNull @Valid
        FamilyMedicalHistoryDTO familyMedicalHistory,

        @NotNull @Valid
        ReasonForVisitDTO reasonForVisit,

        @Valid
        DoctorNotesDTO doctorNotes
) {
}