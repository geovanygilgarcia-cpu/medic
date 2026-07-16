package com.exp.medic.helper.paciente.impl;

import com.exp.medic.dto.paciente.response.PacienteResponseDTO;
import com.exp.medic.helper.paciente.IPacienteHelper;
import com.exp.medic.model.Paciente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PacienteHelperImpl implements IPacienteHelper {

    @Override
    public PacienteResponseDTO mapearEntidadADto(Paciente entidad) {
        return new PacienteResponseDTO(
                entidad.getId(),
                entidad.getExpediente(),
                entidad.getNombreCompleto(),
                calcularIniciales(entidad.getNombreCompleto()),
                calcularEdad(entidad.getFechaNacimiento()),
                entidad.getTelefono(),
                entidad.getFechaNacimiento(),
                entidad.getEmail(),
                entidad.getGenero(),
                entidad.getContactoEmergencia(),
                entidad.getCreatedAt()
        );
    }

    private String calcularIniciales(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            return "";
        }
        String[] partes = nombreCompleto.trim().split("\\s+");
        StringBuilder iniciales = new StringBuilder();
        for (int i = 0; i < Math.min(2, partes.length); i++) {
            if (!partes[i].isEmpty()) {
                iniciales.append(Character.toUpperCase(partes[i].charAt(0)));
            }
        }
        return iniciales.toString();
    }

    private Integer calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return null;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}
