package com.exp.medic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medicamento", nullable = false, length = 200)
    private String medicamento;

    @Column(name = "indicacion", nullable = false, columnDefinition = "TEXT")
    private String indicacion;
}