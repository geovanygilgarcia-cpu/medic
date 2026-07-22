package com.exp.medic.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "citas", indexes = {
        @Index(name = "idx_citas_medico_fecha", columnList = "medico_id, fecha")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Viene del microservicio de auth (apiAuth) — es un String/UUID, igual que
    // medicoOrigenId/medicoDestinoId en ReferenciaResponseDTO.
    @Column(name = "medico_id", nullable = false, length = 64)
    private String medicoId;

    // Denormalizado para no tener que llamar a apiAuth cada vez que se pinta la agenda.
    @Column(name = "medico_nombre", nullable = false, length = 200)
    private String medicoNombre;

    // Opcional: si la cita ya está ligada a un expediente existente.
    @Column(name = "paciente_id")
    private Long pacienteId;

    @Column(name = "paciente_nombre", nullable = false, length = 200)
    private String pacienteNombre;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false, length = 60)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoCita estado = EstadoCita.PENDIENTE;

    @Column(length = 1000)
    private String notas;

    // Se llenará cuando exista sincronización con Google Calendar.
    @Column(name = "google_event_id", length = 200)
    private String googleEventId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}