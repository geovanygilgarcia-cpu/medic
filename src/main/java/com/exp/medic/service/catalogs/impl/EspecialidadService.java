package com.exp.medic.service.catalogs.impl;

import com.exp.medic.dto.catalgos.request.EspecialidadRequestDTO;
import com.exp.medic.dto.catalgos.response.EspecialidadResponseDTO;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.model.Especialidad;
import com.exp.medic.repository.catalogs.JpaEspecialidadRepository;
import com.exp.medic.service.catalogs.IEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadService implements IEspecialidadService {

    private final JpaEspecialidadRepository repository;

    @Override
    public List<EspecialidadResponseDTO> listarTodas() {
        return repository.findAllByOrderByNombre().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public EspecialidadResponseDTO obtenerPorId(Long id) {
        Especialidad especialidad = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con id: " + id));
        return toResponseDTO(especialidad);
    }

    @Override
    public EspecialidadResponseDTO crear(EspecialidadRequestDTO dto) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(dto.getNombre());
        return toResponseDTO(repository.save(especialidad));
    }

    @Override
    public EspecialidadResponseDTO actualizar(Long id, EspecialidadRequestDTO dto) {
        Especialidad especialidad = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con id: " + id));
        especialidad.setNombre(dto.getNombre());
        return toResponseDTO(repository.save(especialidad));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Especialidad no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    private EspecialidadResponseDTO toResponseDTO(Especialidad e) {
        return new EspecialidadResponseDTO(e.getId(), e.getNombre());
    }
}
