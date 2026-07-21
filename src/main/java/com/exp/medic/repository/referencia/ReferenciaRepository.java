package com.exp.medic.repository.referencia;

import com.exp.medic.model.EstadoReferencia;
import com.exp.medic.model.Referencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReferenciaRepository extends JpaRepository<Referencia, Long> {

    List<Referencia> findByMedicoDestinoIdAndEstadoOrderByFechaCreacionDesc(
            String medicoDestinoId, EstadoReferencia estado);

    List<Referencia> findByPacienteIdOrderByFechaCreacionDesc(Long pacienteId);

    void deleteByPacienteId(Long pacienteId);
}