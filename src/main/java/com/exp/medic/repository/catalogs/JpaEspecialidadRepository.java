package com.exp.medic.repository.catalogs;

import com.exp.medic.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaEspecialidadRepository extends JpaRepository<Especialidad, Long> {

    List<Especialidad> findAllByOrderByNombre();
}
