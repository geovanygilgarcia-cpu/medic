package com.exp.medic.helper.receta.impl;

import com.exp.medic.dto.recetas.response.RecetaResponseDTO;
import com.exp.medic.helper.receta.IRecetaMedicaHelper;
import com.exp.medic.model.RecetaMedica;
import org.springframework.stereotype.Component;

@Component
public class RecetaMedicaHelperImpl implements IRecetaMedicaHelper {

    @Override
    public RecetaResponseDTO mapearEntidadADto(RecetaMedica entidad) {
        return new RecetaResponseDTO(
                entidad.getId(),
                entidad.getPacienteId(),
                entidad.getFolio(),
                entidad.getPaciente(),
                entidad.getEdad(),
                entidad.getFecha(),
                entidad.getSignosVitales(),
                entidad.getIdx(),
                entidad.getDiagnosticoTratamiento(),
                entidad.getProximaCita(),
                entidad.getFirma(),
                entidad.getCreatedAt()
        );
    }
}