package com.exp.medic.repository.catalogs;

import com.exp.medic.model.Subespecialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSubespecialidadRepository extends JpaRepository<Subespecialidad, Long> {

    List<Subespecialidad> findByEspecialidadIdOrderByNombre(Long especialidadId);
}
