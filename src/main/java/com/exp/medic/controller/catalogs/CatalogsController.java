package com.exp.medic.controller.catalogs;


import com.exp.medic.dto.catalgos.request.MedicamentoRequestDTO;
import com.exp.medic.dto.catalgos.response.MedicamentoResponseDTO;
import com.exp.medic.service.catalogs.IMedicamentosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class CatalogsController {

    private final IMedicamentosService medicamentosService;

    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> listarTodos(
            @RequestParam(required = false) String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return ResponseEntity.ok(medicamentosService.buscarPorNombre(nombre));
        }
        return ResponseEntity.ok(medicamentosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(medicamentosService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<MedicamentoResponseDTO> crear(@RequestBody MedicamentoRequestDTO dto) {
        MedicamentoResponseDTO creado = medicamentosService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponseDTO> actualizar(
            @PathVariable Long id, @RequestBody MedicamentoRequestDTO dto) {
        return ResponseEntity.ok(medicamentosService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentosService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}