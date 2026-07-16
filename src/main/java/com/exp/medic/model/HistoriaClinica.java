package com.exp.medic.model;

import com.exp.medic.dto.historial.request.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "historia_clinica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id")
    private Long pacienteId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "patient_information", columnDefinition = "jsonb")
    private PatientInformationDTO patientInformation;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "medical_history", columnDefinition = "jsonb")
    private MedicalHistoryDTO medicalHistory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "family_medical_history", columnDefinition = "jsonb")
    private FamilyMedicalHistoryDTO familyMedicalHistory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "reason_for_visit", columnDefinition = "jsonb")
    private ReasonForVisitDTO reasonForVisit;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "doctor_notes", columnDefinition = "jsonb")
    private DoctorNotesDTO doctorNotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}