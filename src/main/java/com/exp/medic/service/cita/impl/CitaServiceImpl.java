package com.exp.medic.service.cita.impl;

import com.exp.medic.dto.cita.CitaRequestDTO;
import com.exp.medic.dto.cita.response.CitaResponseDTO;
import com.exp.medic.exception.CitaNoEncontradaException;
import com.exp.medic.exception.HorarioInvalidoException;
import com.exp.medic.exception.HorarioOcupadoException;
import com.exp.medic.mapper.CitaMapper;
import com.exp.medic.model.Cita;
import com.exp.medic.model.EstadoCita;
import com.exp.medic.repository.cita.CitaRepository;
import com.exp.medic.service.cita.ICitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CitaServiceImpl implements ICitaService {

    private final CitaRepository citaRepository;
    private final CitaMapper citaMapper;

    @Override
    public List<CitaResponseDTO> listarPorMedico(String medicoId, LocalDate desde, LocalDate hasta) {
        List<Cita> citas = (desde != null && hasta != null)
                ? citaRepository.findByMedicoIdAndFechaBetweenOrderByFechaAscHoraInicioAsc(medicoId, desde, hasta)
                : citaRepository.findByMedicoIdOrderByFechaAscHoraInicioAsc(medicoId);

        return citas.stream().map(citaMapper::aResponse).toList();
    }

    @Override
    public CitaResponseDTO obtenerPorId(Long id) {
        return citaMapper.aResponse(buscarOLanzar(id));
    }

    @Override
    @Transactional
    public CitaResponseDTO crear(CitaRequestDTO request) {
        validarHorario(request.getHoraInicio(), request.getHoraFin());
        validarSinTraslape(request.getMedicoId(), request.getFecha(),
                request.getHoraInicio(), request.getHoraFin(), 0L);

        Cita nueva = citaMapper.aEntidadNueva(request);
        Cita guardada = citaRepository.save(nueva);
        return citaMapper.aResponse(guardada);
    }

    @Override
    @Transactional
    public CitaResponseDTO actualizar(Long id, CitaRequestDTO request) {
        Cita existente = buscarOLanzar(id);

        validarHorario(request.getHoraInicio(), request.getHoraFin());
        validarSinTraslape(request.getMedicoId(), request.getFecha(),
                request.getHoraInicio(), request.getHoraFin(), id);

        citaMapper.actualizarEntidad(existente, request);
        return citaMapper.aResponse(citaRepository.save(existente));
    }

    @Override
    @Transactional
    public CitaResponseDTO cambiarEstado(Long id, EstadoCita estado) {
        Cita existente = buscarOLanzar(id);
        existente.setEstado(estado);
        return citaMapper.aResponse(citaRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new CitaNoEncontradaException(id.toString());
        }
        citaRepository.deleteById(id);
    }

    @Override
    public Optional<CitaResponseDTO> obtenerProximaCita(String medicoId) {
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.now();
        return citaRepository.buscarProximaCita(medicoId, hoy, ahora)
                .map(citaMapper::aResponse);
    }

    private Cita buscarOLanzar(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new CitaNoEncontradaException(id.toString()));
    }

    private void validarHorario(LocalTime horaInicio, LocalTime horaFin) {
        if (horaFin == null || horaInicio == null || !horaFin.isAfter(horaInicio)) {
            throw new HorarioInvalidoException("La hora de fin debe ser posterior a la hora de inicio.");
        }
    }

    private void validarSinTraslape(String medicoId, LocalDate fecha, LocalTime horaInicio,
                                    LocalTime horaFin, Long idExcluir) {
        List<Cita> traslapes = citaRepository.buscarTraslapes(
                medicoId, fecha, horaInicio, horaFin, idExcluir, EstadoCita.CANCELADA);

        if (!traslapes.isEmpty()) {
            throw new HorarioOcupadoException(
                    "El médico ya tiene una cita en ese horario (" + traslapes.get(0).getPacienteNombre() + ").");
        }
    }
}
