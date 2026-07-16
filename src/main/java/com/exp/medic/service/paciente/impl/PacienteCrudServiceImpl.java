package com.exp.medic.service.paciente.impl;

import com.exp.medic.dto.paciente.request.PacienteDTO;
import com.exp.medic.dto.paciente.response.PacienteResponseDTO;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.helper.paciente.IPacienteHelper;
import com.exp.medic.model.Paciente;
import com.exp.medic.repository.paciente.PacienteRepository;
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

    @Override
    @Transactional
    public PacienteResponseDTO crear(PacienteDTO dto) {
        Paciente entidad = new Paciente();
        mapearDtoAEntidad(dto, entidad);

        Paciente guardado = pacienteRepository.save(entidad);
        return iPacienteHelper.mapearEntidadADto(guardado);
    }

    @Override
    @Transactional
    public PacienteResponseDTO actualizar(Long id, PacienteDTO dto) {
        Paciente entidad = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));

        mapearDtoAEntidad(dto, entidad);

        Paciente actualizado = pacienteRepository.save(entidad);
        return iPacienteHelper.mapearEntidadADto(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodos() {
        return pacienteRepository.findAll()
                .stream()
                .map(iPacienteHelper::mapearEntidadADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDTO obtenerPorId(Long id) {
        Paciente entidad = pacienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Paciente no encontrado con id: " + id));
        return iPacienteHelper.mapearEntidadADto(entidad);
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
