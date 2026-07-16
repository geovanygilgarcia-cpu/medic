package com.exp.medic.repository.historia;

import com.exp.medic.model.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {

    List<HistoriaClinica> findByPacienteIdOrderByCreatedAtDesc(Long pacienteId);
}