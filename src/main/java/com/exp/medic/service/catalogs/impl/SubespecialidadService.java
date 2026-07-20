package com.exp.medic.service.catalogs.impl;

import com.exp.medic.dto.catalgos.request.SubespecialidadRequestDTO;
import com.exp.medic.dto.catalgos.response.SubespecialidadResponseDTO;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.model.Especialidad;
import com.exp.medic.model.Subespecialidad;
import com.exp.medic.repository.catalogs.JpaEspecialidadRepository;
import com.exp.medic.repository.catalogs.JpaSubespecialidadRepository;
import com.exp.medic.service.catalogs.ISubespecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubespecialidadService implements ISubespecialidadService {

    private final JpaSubespecialidadRepository repository;
    private final JpaEspecialidadRepository especialidadRepository;

    @Override
    public List<SubespecialidadResponseDTO> listarTodas(Long especialidadId) {
        List<Subespecialidad> resultado = (especialidadId != null)
                ? repository.findByEspecialidadIdOrderByNombre(especialidadId)
                : repository.findAll();

        return resultado.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public SubespecialidadResponseDTO obtenerPorId(Long id) {
        Subespecialidad subespecialidad = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Subespecialidad no encontrada con id: " + id));
        return toResponseDTO(subespecialidad);
    }

    @Override
    public SubespecialidadResponseDTO crear(SubespecialidadRequestDTO dto) {
        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Especialidad no encontrada con id: " + dto.getEspecialidadId()));

        Subespecialidad subespecialidad = new Subespecialidad();
        subespecialidad.setNombre(dto.getNombre());
        subespecialidad.setEspecialidad(especialidad);

        return toResponseDTO(repository.save(subespecialidad));
    }

    @Override
    public SubespecialidadResponseDTO actualizar(Long id, SubespecialidadRequestDTO dto) {
        Subespecialidad subespecialidad = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Subespecialidad no encontrada con id: " + id));

        Especialidad especialidad = especialidadRepository.findById(dto.getEspecialidadId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Especialidad no encontrada con id: " + dto.getEspecialidadId()));

        subespecialidad.setNombre(dto.getNombre());
        subespecialidad.setEspecialidad(especialidad);

        return toResponseDTO(repository.save(subespecialidad));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Subespecialidad no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    private SubespecialidadResponseDTO toResponseDTO(Subespecialidad s) {
        return new SubespecialidadResponseDTO(
                s.getId(),
                s.getNombre(),
                s.getEspecialidad().getId(),
                s.getEspecialidad().getNombre()
        );
    }
}
