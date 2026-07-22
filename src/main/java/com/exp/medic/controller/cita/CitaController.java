package com.exp.medic.controller.cita;

import com.exp.medic.dto.cita.CambiarEstadoRequestDTO;
import com.exp.medic.dto.cita.CitaRequestDTO;
import com.exp.medic.dto.cita.response.CitaResponseDTO;
import com.exp.medic.service.cita.ICitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final ICitaService citaService;

    // GET /api/citas?medicoId=xxx&desde=2026-07-20&hasta=2026-07-26
    // desde/hasta son opcionales: si se omiten, devuelve toda la agenda del médico.
    @GetMapping
    public ResponseEntity<List<CitaResponseDTO>> listar(
            @RequestParam String medicoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(citaService.listarPorMedico(medicoId, desde, hasta));
    }

    // GET /api/citas/proxima?medicoId=xxx
    @GetMapping("/proxima")
    public ResponseEntity<CitaResponseDTO> proxima(@RequestParam String medicoId) {
        return citaService.obtenerProximaCita(medicoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<CitaResponseDTO> crear(@Valid @RequestBody CitaRequestDTO request) {
        CitaResponseDTO creada = citaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody CitaRequestDTO request) {
        return ResponseEntity.ok(citaService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CitaResponseDTO> cambiarEstado(
            @PathVariable Long id, @Valid @RequestBody CambiarEstadoRequestDTO request) {
        return ResponseEntity.ok(citaService.cambiarEstado(id, request.getEstado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
