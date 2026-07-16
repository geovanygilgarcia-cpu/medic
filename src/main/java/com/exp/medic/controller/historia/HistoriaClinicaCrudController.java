package com.exp.medic.controller.historia;

import com.exp.medic.dto.historial.request.PatientIntakeFormDTO;
import com.exp.medic.dto.historial.response.HistoriaClinicaResponseDTO;
import com.exp.medic.service.historia.IHistoriaClinicaCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historias")
@RequiredArgsConstructor
public class HistoriaClinicaCrudController {

    private final IHistoriaClinicaCrudService historiaClinicaCrudService;

    @PostMapping
    public ResponseEntity<HistoriaClinicaResponseDTO> crear(@Valid @RequestBody PatientIntakeFormDTO dto) {
        HistoriaClinicaResponseDTO creada = historiaClinicaCrudService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PatientIntakeFormDTO dto) {
        HistoriaClinicaResponseDTO actualizada = historiaClinicaCrudService.actualizar(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        historiaClinicaCrudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(historiaClinicaCrudService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(historiaClinicaCrudService.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistoriaClinicaResponseDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historiaClinicaCrudService.listarPorPaciente(pacienteId));
    }
}