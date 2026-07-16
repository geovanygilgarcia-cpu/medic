package com.exp.medic.repository.paciente;

import com.exp.medic.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByExpediente(String expediente);

    boolean existsByExpediente(String expediente);
}
