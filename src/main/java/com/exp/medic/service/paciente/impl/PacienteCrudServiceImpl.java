package com.exp.medic.service.paciente.impl;

import com.exp.medic.dto.paciente.request.PacienteDTO;
import com.exp.medic.dto.paciente.response.PacienteResponseDTO;
import com.exp.medic.exception.AccesoDenegadoException;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.helper.paciente.IPacienteHelper;
import com.exp.medic.model.Paciente;
import com.exp.medic.repository.historia.HistoriaClinicaRepository;
import com.exp.medic.repository.paciente.PacienteRepository;
import com.exp.medic.repository.recetas.RecetaMedicaRepository;
import com.exp.medic.repository.referencia.ReferenciaRepository;
import com.exp.medic.security.CurrentUser;
import com.exp.medic.security.CurrentUserContext;
import com.exp.medic.service.paciente.IPacienteCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteCrudServiceImpl implements IPacienteCrudService {

    private final PacienteRepository pacienteRepository;
    private final IPacienteHelper iPacienteHelper;
    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final RecetaMedicaRepository recetaMedicaRepository;
    private final ReferenciaRepository referenciaRepository;

    @Override
    @Transactional
    public PacienteResponseDTO crear(PacienteDTO dto) {
        Paciente entidad = new Paciente();
        mapearDtoAEntidad(dto, entidad);

        // El paciente queda asociado a quien lo registra (normalmente el
        // médico). No confiamos en nada que venga en el body para esto,
        // solo en la identidad que ya validamos del JWT.
        CurrentUser usuarioActual = CurrentUserContext.get();
        if (usuarioActual != null) {
            entidad.setMedicoId(usuarioActual.id());
            entidad.setMedicoNombre(usuarioActual.nombreCompleto());
        }

        Paciente guardado = pacienteRepository.save(entidad);
        return iPacienteHelper.mapearEntidadADto(guardado);
    }

    @Override
    @Transactional
    public PacienteResponseDTO actualizar(Long id, PacienteDTO dto) {
        Paciente entidad = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));

        verificarAccesoAlPaciente(entidad);

        mapearDtoAEntidad(dto, entidad);
        // El médico tratante no se toca en una actualización de datos
        // generales; si algún día quieres reasignar pacientes, eso debería
        // ser una acción explícita aparte, no un efecto secundario del PUT.

        Paciente actualizado = pacienteRepository.save(entidad);
        return iPacienteHelper.mapearEntidadADto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Paciente entidad = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));

        verificarAccesoAlPaciente(entidad);

        recetaMedicaRepository.deleteByPacienteId(id);
        historiaClinicaRepository.deleteByPacienteId(id);
        // Sin esto, el DELETE del paciente truena por la llave foránea
        // referencias_paciente_id_fkey si alguna vez fue referido/aceptado.
        referenciaRepository.deleteByPacienteId(id);

        pacienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodos() {
        CurrentUser usuarioActual = CurrentUserContext.get();

        List<Paciente> pacientes = (usuarioActual != null && usuarioActual.esMedico())
                ? pacienteRepository.findByMedicoId(usuarioActual.id())
                : pacienteRepository.findAll();

        return pacientes.stream()
                .map(iPacienteHelper::mapearEntidadADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPorId(Long id) {
        Paciente entidad = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));

        verificarAccesoAlPaciente(entidad);

        return iPacienteHelper.mapearEntidadADto(entidad);
    }

    /**
     * Un MEDICO solo puede ver/editar/eliminar a sus propios pacientes.
     * ADMIN, RECEPCION y ENFERMERA no se restringen aquí (front desk y
     * administración necesitan ver a todos).
     */
    private void verificarAccesoAlPaciente(Paciente entidad) {
        CurrentUser usuarioActual = CurrentUserContext.get();
        if (usuarioActual == null || !usuarioActual.esMedico()) {
            return;
        }

        boolean esSuPaciente = usuarioActual.id().equals(entidad.getMedicoId());
        if (!esSuPaciente) {
            throw new AccesoDenegadoException("Este paciente no está asignado a tu usuario.");
        }
    }

    private void mapearDtoAEntidad(PacienteDTO dto, Paciente entidad) {
        entidad.setExpediente(dto.expediente());
        entidad.setNombreCompleto(dto.nombreCompleto());
        entidad.setTelefono(dto.telefono());
        entidad.setFechaNacimiento(dto.fechaNacimiento());
        entidad.setEmail(dto.email());
        entidad.setGenero(dto.genero());
        entidad.setContactoEmergencia(dto.contactoEmergencia());
    }
}