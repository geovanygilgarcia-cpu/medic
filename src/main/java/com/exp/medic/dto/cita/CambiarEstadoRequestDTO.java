package com.exp.medic.dto.cita;

import com.exp.medic.model.EstadoCita;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoRequestDTO {

    @NotNull(message = "El estado es obligatorio")
    private EstadoCita estado;
}