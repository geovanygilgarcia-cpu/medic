package com.exp.medic.repository.recetas;

import com.exp.medic.model.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Long> {

    List<RecetaMedica> findByPacienteIdOrderByCreatedAtDesc(Long pacienteId);
}