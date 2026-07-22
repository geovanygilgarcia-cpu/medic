package com.exp.medic.mapper;

import com.exp.medic.dto.cita.CitaRequestDTO;
import com.exp.medic.dto.cita.response.CitaResponseDTO;
import com.exp.medic.model.Cita;
import com.exp.medic.model.EstadoCita;
import org.springframework.stereotype.Component;

@Component
public class CitaMapper {

    public Cita aEntidadNueva(CitaRequestDTO dto) {
        return Cita.builder()
                .medicoId(dto.getMedicoId())
                .medicoNombre(dto.getMedicoNombre())
                .pacienteId(dto.getPacienteId())
                .pacienteNombre(dto.getPacienteNombre())
                .fecha(dto.getFecha())
                .horaInicio(dto.getHoraInicio())
                .horaFin(dto.getHoraFin())
                .tipo(dto.getTipo())
                .estado(dto.getEstado() != null ? dto.getEstado() : EstadoCita.PENDIENTE)
                .notas(dto.getNotas())
                .build();
    }

    public void actualizarEntidad(Cita cita, CitaRequestDTO dto) {
        cita.setMedicoId(dto.getMedicoId());
        cita.setMedicoNombre(dto.getMedicoNombre());
        cita.setPacienteId(dto.getPacienteId());
        cita.setPacienteNombre(dto.getPacienteNombre());
        cita.setFecha(dto.getFecha());
        cita.setHoraInicio(dto.getHoraInicio());
        cita.setHoraFin(dto.getHoraFin());
        cita.setTipo(dto.getTipo());
        cita.setEstado(dto.getEstado());
        cita.setNotas(dto.getNotas());
    }

    public CitaResponseDTO aResponse(Cita cita) {
        return CitaResponseDTO.builder()
                .id(cita.getId())
                .medicoId(cita.getMedicoId())
                .medicoNombre(cita.getMedicoNombre())
                .pacienteId(cita.getPacienteId())
                .pacienteNombre(cita.getPacienteNombre())
                .fecha(cita.getFecha())
                .horaInicio(cita.getHoraInicio())
                .horaFin(cita.getHoraFin())
                .tipo(cita.getTipo())
                .estado(cita.getEstado())
                .notas(cita.getNotas())
                .googleEventId(cita.getGoogleEventId())
                .build();
    }
}
