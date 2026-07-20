package com.exp.medic.dto.catalgos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubespecialidadRequestDTO {
    private String nombre;
    private Long especialidadId;
}
