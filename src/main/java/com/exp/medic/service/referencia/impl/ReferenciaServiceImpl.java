package com.exp.medic.service.referencia.impl;

import com.exp.medic.dto.referencia.request.ReferenciaRequestDTO;
import com.exp.medic.dto.referencia.response.ReferenciaResponseDTO;
import com.exp.medic.exception.AccesoDenegadoException;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.model.EstadoReferencia;
import com.exp.medic.model.Paciente;
import com.exp.medic.model.Referencia;
import com.exp.medic.repository.paciente.PacienteRepository;
import com.exp.medic.repository.referencia.ReferenciaRepository;
import com.exp.medic.security.CurrentUser;
import com.exp.medic.security.CurrentUserContext;
import com.exp.medic.service.referencia.IReferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReferenciaServiceImpl implements IReferenciaService {

    private final ReferenciaRepository referenciaRepository;
    private final PacienteRepository pacienteRepository;

    @Override
    @Transactional
    public ReferenciaResponseDTO crear(ReferenciaRequestDTO dto) {
        CurrentUser usuarioActual = requerirMedicoActual();

        Paciente paciente = pacienteRepository.findById(dto.pacienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Paciente no encontrado con id: " + dto.pacienteId()));

        // Solo el médico dueño del paciente puede referirlo (mismo criterio
        // que usa PacienteCrudServiceImpl para editar/eliminar).
        boolean esSuPaciente = usuarioActual.id().equals(paciente.getMedicoId());
        if (!esSuPaciente) {
            throw new AccesoDenegadoException("Este paciente no está asignado a tu usuario.");
        }

        if (usuarioActual.id().equals(dto.medicoDestinoId())) {
            throw new AccesoDenegadoException("No puedes referir un paciente a ti mismo.");
        }

        Referencia entidad = new Referencia();
        entidad.setPacienteId(paciente.getId());
        entidad.setPacienteNombre(paciente.getNombreCompleto());
        // El médico origen se toma del JWT, nunca del body.
        entidad.setMedicoOrigenId(usuarioActual.id());
        entidad.setMedicoOrigenNombre(usuarioActual.nombreCompleto());
        entidad.setMedicoDestinoId(dto.medicoDestinoId());
        entidad.setMedicoDestinoNombre(dto.medicoDestinoNombre());
        entidad.setMotivo(dto.motivo());
        entidad.setEstado(EstadoReferencia.PENDIENTE);
        entidad.setFechaCreacion(LocalDateTime.now());

        Referencia guardada = referenciaRepository.save(entidad);
        return mapearADto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferenciaResponseDTO> listarPendientesParaMiUsuario() {
        CurrentUser usuarioActual = CurrentUserContext.get();
        if (usuarioActual == null) {
            return List.of();
        }

        return referenciaRepository
                .findByMedicoDestinoIdAndEstadoOrderByFechaCreacionDesc(
                        usuarioActual.id(), EstadoReferencia.PENDIENTE)
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Override
    @Transactional
    public ReferenciaResponseDTO aceptar(Long id) {
        Referencia entidad = obtenerPendientePropia(id);

        // Transferencia completa: el paciente pasa a ser del médico destino
        // y deja de aparecer en la lista del médico que refirió.
        Paciente paciente = pacienteRepository.findById(entidad.getPacienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Paciente no encontrado con id: " + entidad.getPacienteId()));
        paciente.setMedicoId(entidad.getMedicoDestinoId());
        paciente.setMedicoNombre(entidad.getMedicoDestinoNombre());
        pacienteRepository.save(paciente);

        entidad.setEstado(EstadoReferencia.ACEPTADA);
        entidad.setFechaResolucion(LocalDateTime.now());

        Referencia guardada = referenciaRepository.save(entidad);
        return mapearADto(guardada);
    }

    @Override
    @Transactional
    public ReferenciaResponseDTO rechazar(Long id) {
        Referencia entidad = obtenerPendientePropia(id);

        entidad.setEstado(EstadoReferencia.RECHAZADA);
        entidad.setFechaResolucion(LocalDateTime.now());

        Referencia guardada = referenciaRepository.save(entidad);
        return mapearADto(guardada);
    }

    /** Busca la referencia y valida que esté PENDIENTE y dirigida al usuario actual. */
    private Referencia obtenerPendientePropia(Long id) {
        CurrentUser usuarioActual = requerirMedicoActual();

        Referencia entidad = referenciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Referencia no encontrada con id: " + id));

        if (!usuarioActual.id().equals(entidad.getMedicoDestinoId())) {
            throw new AccesoDenegadoException("Esta referencia no fue enviada a tu usuario.");
        }

        if (entidad.getEstado() != EstadoReferencia.PENDIENTE) {
            throw new AccesoDenegadoException("Esta referencia ya fue resuelta.");
        }

        return entidad;
    }

    private CurrentUser requerirMedicoActual() {
        CurrentUser usuarioActual = CurrentUserContext.get();
        if (usuarioActual == null || !usuarioActual.esMedico()) {
            throw new AccesoDenegadoException("Solo un médico puede referir o resolver referencias.");
        }
        return usuarioActual;
    }

    private ReferenciaResponseDTO mapearADto(Referencia r) {
        return new ReferenciaResponseDTO(
                r.getId(),
                r.getPacienteId(),
                r.getPacienteNombre(),
                r.getMedicoOrigenId(),
                r.getMedicoOrigenNombre(),
                r.getMedicoDestinoId(),
                r.getMedicoDestinoNombre(),
                r.getMotivo(),
                r.getEstado(),
                r.getFechaCreacion(),
                r.getFechaResolucion()
        );
    }
}