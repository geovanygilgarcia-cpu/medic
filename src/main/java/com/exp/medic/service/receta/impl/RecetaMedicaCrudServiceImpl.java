package com.exp.medic.service.receta.impl;

import com.exp.medic.dto.recetas.request.RecetaDTO;
import com.exp.medic.dto.recetas.response.RecetaResponseDTO;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.helper.receta.IRecetaMedicaHelper;
import com.exp.medic.model.RecetaMedica;
import com.exp.medic.repository.recetas.RecetaMedicaRepository;
import com.exp.medic.service.receta.IRecetaMedicaCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecetaMedicaCrudServiceImpl implements IRecetaMedicaCrudService {

    private final RecetaMedicaRepository recetaMedicaRepository;
    private final IRecetaMedicaHelper iRecetaMedicaHelper;

    @Override
    @Transactional
    public RecetaResponseDTO crear(RecetaDTO dto) {
        RecetaMedica entidad = new RecetaMedica();
        mapearDtoAEntidad(dto, entidad);

        RecetaMedica guardada = recetaMedicaRepository.save(entidad);
        return iRecetaMedicaHelper.mapearEntidadADto(guardada);
    }

    @Override
    @Transactional
    public RecetaResponseDTO actualizar(Long id, RecetaDTO dto) {
        RecetaMedica entidad = recetaMedicaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Receta no encontrada con id: " + id));

        mapearDtoAEntidad(dto, entidad);

        RecetaMedica actualizada = recetaMedicaRepository.save(entidad);
        return iRecetaMedicaHelper.mapearEntidadADto(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!recetaMedicaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Receta no encontrada con id: " + id);
        }
        recetaMedicaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarTodas() {
        return recetaMedicaRepository.findAll()
                .stream()
                .map(iRecetaMedicaHelper::mapearEntidadADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecetaResponseDTO obtenerPorId(Long id) {
        RecetaMedica entidad = recetaMedicaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Receta no encontrada con id: " + id));
        return iRecetaMedicaHelper.mapearEntidadADto(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecetaResponseDTO> listarPorPaciente(Long pacienteId) {
        return recetaMedicaRepository.findByPacienteIdOrderByCreatedAtDesc(pacienteId)
                .stream()
                .map(iRecetaMedicaHelper::mapearEntidadADto)
                .toList();
    }

    private void mapearDtoAEntidad(RecetaDTO dto, RecetaMedica entidad) {
        entidad.setPacienteId(dto.pacienteId());
        entidad.setFolio(dto.folio());
        entidad.setPaciente(dto.paciente());
        entidad.setEdad(dto.edad());
        entidad.setFecha(dto.fecha());
        entidad.setSignosVitales(dto.signosVitales());
        entidad.setIdx(dto.idx());
        entidad.setDiagnosticoTratamiento(dto.diagnosticoTratamiento());
        entidad.setProximaCita(dto.proximaCita());
        entidad.setFirma(dto.firma());
    }

}