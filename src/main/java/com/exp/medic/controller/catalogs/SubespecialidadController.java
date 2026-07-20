package com.exp.medic.controller.catalogs;

import com.exp.medic.dto.catalgos.request.SubespecialidadRequestDTO;
import com.exp.medic.dto.catalgos.response.SubespecialidadResponseDTO;
import com.exp.medic.service.catalogs.ISubespecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subespecialidades")
@RequiredArgsConstructor
public class SubespecialidadController {

    private final ISubespecialidadService subespecialidadService;

    @GetMapping
    public ResponseEntity<List<SubespecialidadResponseDTO>> listarTodas(
            @RequestParam(required = false) Long especialidadId) {
        return ResponseEntity.ok(subespecialidadService.listarTodas(especialidadId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubespecialidadResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(subespecialidadService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<SubespecialidadResponseDTO> crear(@RequestBody SubespecialidadRequestDTO dto) {
        SubespecialidadResponseDTO creada = subespecialidadService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubespecialidadResponseDTO> actualizar(
            @PathVariable Long id, @RequestBody SubespecialidadRequestDTO dto) {
        return ResponseEntity.ok(subespecialidadService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        subespecialidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
