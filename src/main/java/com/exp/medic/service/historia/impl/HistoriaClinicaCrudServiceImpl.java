package com.exp.medic.service.historia.impl;

import com.exp.medic.dto.historial.request.PatientIntakeFormDTO;
import com.exp.medic.dto.historial.response.HistoriaClinicaResponseDTO;
import com.exp.medic.exception.RecursoNoEncontradoException;
import com.exp.medic.helper.historia.IHistoriaMedicaHelper;
import com.exp.medic.model.HistoriaClinica;
import com.exp.medic.repository.historia.HistoriaClinicaRepository;
import com.exp.medic.service.historia.IHistoriaClinicaCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriaClinicaCrudServiceImpl implements IHistoriaClinicaCrudService {

    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final IHistoriaMedicaHelper iHistoriaMedicaHelper;

    @Override
    @Transactional
    public HistoriaClinicaResponseDTO crear(PatientIntakeFormDTO dto) {
        HistoriaClinica entidad = new HistoriaClinica();
        mapearDtoAEntidad(dto, entidad);

        HistoriaClinica guardada = historiaClinicaRepository.save(entidad);
        return iHistoriaMedicaHelper.mapearEntidadADto(guardada);
    }

    @Override
    @Transactional
    public HistoriaClinicaResponseDTO actualizar(Long id, PatientIntakeFormDTO dto) {
        HistoriaClinica entidad = historiaClinicaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Historia clínica no encontrada con id: " + id));

        mapearDtoAEntidad(dto, entidad);

        HistoriaClinica actualizada = historiaClinicaRepository.save(entidad);
        return iHistoriaMedicaHelper.mapearEntidadADto(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!historiaClinicaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Historia clínica no encontrada con id: " + id);
        }
        historiaClinicaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinicaResponseDTO> listarTodas() {
        return historiaClinicaRepository.findAll()
                .stream()
                .map(iHistoriaMedicaHelper::mapearEntidadADto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoriaClinicaResponseDTO obtenerPorId(Long id) {
        HistoriaClinica entidad = historiaClinicaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Historia clínica no encontrada con id: " + id));
        return iHistoriaMedicaHelper.mapearEntidadADto(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoriaClinicaResponseDTO> listarPorPaciente(Long pacienteId) {
        return historiaClinicaRepository.findByPacienteIdOrderByCreatedAtDesc(pacienteId)
                .stream()
                .map(iHistoriaMedicaHelper::mapearEntidadADto)
                .toList();
    }

    private void mapearDtoAEntidad(PatientIntakeFormDTO dto, HistoriaClinica entidad) {
        entidad.setPacienteId(dto.pacienteId());
        entidad.setPatientInformation(dto.patientInformation());
        entidad.setMedicalHistory(dto.medicalHistory());
        entidad.setFamilyMedicalHistory(dto.familyMedicalHistory());
        entidad.setReasonForVisit(dto.reasonForVisit());
        entidad.setDoctorNotes(dto.doctorNotes());
    }
}