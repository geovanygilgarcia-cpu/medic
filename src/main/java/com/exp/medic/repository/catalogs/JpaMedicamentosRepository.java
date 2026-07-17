package com.exp.medic.repository.catalogs;

import com.exp.medic.model.Medicamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMedicamentosRepository extends JpaRepository<Medicamentos, Long> {

    List<Medicamentos> findByMedicamentoContainingIgnoreCase(String medicamento);
}