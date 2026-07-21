package com.exp.medic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "referencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Referencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    // Denormalizado a propósito, igual que medicoNombre en Paciente: evita
    // ir a buscar el paciente por id cada vez que se lista una referencia.
    @Column(name = "paciente_nombre", nullable = false, length = 200)
    private String pacienteNombre;

    // Igual que medicoId en Paciente: es el UUID del Usuario (auth-service),
    // no una FK real porque son bases de datos distintas.
    @Column(name = "medico_origen_id", nullable = false, length = 36)
    private String medicoOrigenId;

    @Column(name = "medico_origen_nombre", nullable = false, length = 255)
    private String medicoOrigenNombre;

    @Column(name = "medico_destino_id", nullable = false, length = 36)
    private String medicoDestinoId;

    @Column(name = "medico_destino_nombre", nullable = false, length = 255)
    private String medicoDestinoNombre;

    @Column(name = "motivo", length = 1000)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoReferencia estado = EstadoReferencia.PENDIENTE;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;
}