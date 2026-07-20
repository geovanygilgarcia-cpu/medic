package com.exp.medic.dto.catalgos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubespecialidadResponseDTO {
    private Long id;
    private String nombre;
    private Long especialidadId;
    private String especialidadNombre;
}
