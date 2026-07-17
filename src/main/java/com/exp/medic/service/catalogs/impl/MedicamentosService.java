package com.exp.medic.service.catalogs.impl;

import com.exp.medic.dto.catalgos.request.MedicamentoRequestDTO;
import com.exp.medic.dto.catalgos.response.MedicamentoResponseDTO;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.model.Medicamentos;
import com.exp.medic.repository.catalogs.JpaMedicamentosRepository;
import com.exp.medic.service.catalogs.IMedicamentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicamentosService implements IMedicamentosService {

    private final JpaMedicamentosRepository repository;

    @Override
    public List<MedicamentoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public List<MedicamentoResponseDTO> buscarPorNombre(String nombre) {
        return repository.findByMedicamentoContainingIgnoreCase(nombre).stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public MedicamentoResponseDTO obtenerPorId(Long id) {
        Medicamentos medicamento = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Medicamento no encontrado con id: " + id));
        return toResponseDTO(medicamento);
    }

    @Override
    public MedicamentoResponseDTO crear(MedicamentoRequestDTO dto) {
        Medicamentos medicamento = new Medicamentos();
        medicamento.setMedicamento(dto.getMedicamento());
        medicamento.setIndicacion(dto.getIndicacion());
        return toResponseDTO(repository.save(medicamento));
    }

    @Override
    public MedicamentoResponseDTO actualizar(Long id, MedicamentoRequestDTO dto) {
        Medicamentos medicamento = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Medicamento no encontrado con id: " + id));
        medicamento.setMedicamento(dto.getMedicamento());
        medicamento.setIndicacion(dto.getIndicacion());
        return toResponseDTO(repository.save(medicamento));
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Medicamento no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private MedicamentoResponseDTO toResponseDTO(Medicamentos m) {
        return new MedicamentoResponseDTO(m.getId(), m.getMedicamento(), m.getIndicacion());
    }
}
