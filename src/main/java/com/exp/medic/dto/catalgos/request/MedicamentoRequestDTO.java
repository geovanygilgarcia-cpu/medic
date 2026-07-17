package com.exp.medic.dto.catalgos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicamentoRequestDTO {
    private String medicamento;
    private String indicacion;
}
