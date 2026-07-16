package com.exp.medic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expediente", unique = true, nullable = false, length = 20)
    private String expediente;

    @Column(name = "nombre_completo", nullable = false, length = 200)
    private String nombreCompleto;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "genero", length = 10)
    private String genero;

    @Column(name = "contacto_emergencia", length = 200)
    private String contactoEmergencia;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
