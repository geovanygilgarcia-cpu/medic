package com.exp.medic.repository.cita;

import com.exp.medic.model.Cita;
import com.exp.medic.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMedicoIdAndFechaBetweenOrderByFechaAscHoraInicioAsc(
            String medicoId, LocalDate desde, LocalDate hasta);

    List<Cita> findByMedicoIdOrderByFechaAscHoraInicioAsc(String medicoId);

    @Query("""
        select c from Cita c
        where c.medicoId = :medicoId
          and c.estado <> :cancelada
          and (c.fecha > :hoy or (c.fecha = :hoy and c.horaInicio >= :horaActual))
        order by c.fecha asc, c.horaInicio asc
        """)
    List<Cita> buscarProximas(
            @Param("medicoId") String medicoId,
            @Param("hoy") LocalDate hoy,
            @Param("horaActual") LocalTime horaActual,
            @Param("cancelada") EstadoCita cancelada);

    default Optional<Cita> buscarProximaCita(String medicoId, LocalDate hoy, LocalTime horaActual) {
        return buscarProximas(medicoId, hoy, horaActual, EstadoCita.CANCELADA)
                .stream()
                .findFirst();
    }

    @Query("""
        select c from Cita c
        where c.medicoId = :medicoId
          and c.fecha = :fecha
          and c.estado <> :cancelada
          and c.id <> :idExcluir
          and c.horaInicio < :horaFin
          and c.horaFin > :horaInicio
        """)
    List<Cita> buscarTraslapes(
            @Param("medicoId") String medicoId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("idExcluir") Long idExcluir,
            @Param("cancelada") EstadoCita cancelada);
}
