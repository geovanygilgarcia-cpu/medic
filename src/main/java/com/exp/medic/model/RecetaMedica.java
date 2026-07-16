package com.exp.medic.model;

import com.exp.medic.dto.recetas.request.FechaDTO;
import com.exp.medic.dto.recetas.request.SignosVitalesDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "receta_medica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecetaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id")
    private Long pacienteId;

    @Column(name = "folio", unique = true, nullable = false, length = 30)
    private String folio;

    @Column(name = "paciente", nullable = false, length = 200)
    private String paciente;

    @Column(name = "edad", length = 10)
    private String edad;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "fecha", columnDefinition = "jsonb")
    private FechaDTO fecha;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "signos_vitales", columnDefinition = "jsonb")
    private SignosVitalesDTO signosVitales;

    @Column(name = "idx", columnDefinition = "TEXT")
    private String idx;

    @Column(name = "diagnostico_tratamiento", columnDefinition = "TEXT")
    private String diagnosticoTratamiento;

    @Column(name = "proxima_cita", length = 10)
    private String proximaCita;

    @Column(name = "firma", length = 200)
    private String firma;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}