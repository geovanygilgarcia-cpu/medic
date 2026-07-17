package com.exp.medic.dto.catalgos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoResponseDTO {
    private Long id;
    private String medicamento;
    private String indicacion;
}
