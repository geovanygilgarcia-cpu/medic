package com.exp.medic.controller.recetas;

import com.exp.medic.dto.recetas.request.RecetaDTO;
import com.exp.medic.dto.recetas.response.RecetaResponseDTO;
import com.exp.medic.service.receta.IRecetaMedicaCrudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@RequiredArgsConstructor
public class RecetaMedicaCrudController {

    private final IRecetaMedicaCrudService recetaMedicaCrudService;

    @PostMapping
    public ResponseEntity<RecetaResponseDTO> crear(@Valid @RequestBody RecetaDTO dto) {
        RecetaResponseDTO creada = recetaMedicaCrudService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecetaDTO dto) {
        RecetaResponseDTO actualizada = recetaMedicaCrudService.actualizar(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        recetaMedicaCrudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(recetaMedicaCrudService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recetaMedicaCrudService.obtenerPorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<RecetaResponseDTO>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(recetaMedicaCrudService.listarPorPaciente(pacienteId));
    }
}