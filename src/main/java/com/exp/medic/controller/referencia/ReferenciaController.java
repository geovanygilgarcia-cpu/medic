package com.exp.medic.controller.referencia;

import com.exp.medic.dto.referencia.request.ReferenciaRequestDTO;
import com.exp.medic.dto.referencia.response.ReferenciaResponseDTO;
import com.exp.medic.service.referencia.IReferenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referencias")
@RequiredArgsConstructor
public class ReferenciaController {

    private final IReferenciaService referenciaService;

    @PostMapping
    public ResponseEntity<ReferenciaResponseDTO> crear(@Valid @RequestBody ReferenciaRequestDTO dto) {
        ReferenciaResponseDTO creada = referenciaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /** Referencias pendientes dirigidas al médico logueado (para la campanita de notificaciones). */
    @GetMapping("/pendientes")
    public ResponseEntity<List<ReferenciaResponseDTO>> listarPendientes() {
        return ResponseEntity.ok(referenciaService.listarPendientesParaMiUsuario());
    }

    @PutMapping("/{id}/aceptar")
    public ResponseEntity<ReferenciaResponseDTO> aceptar(@PathVariable Long id) {
        return ResponseEntity.ok(referenciaService.aceptar(id));
    }

    @PutMapping("/{id}/rechazar")
    public ResponseEntity<ReferenciaResponseDTO> rechazar(@PathVariable Long id) {
        return ResponseEntity.ok(referenciaService.rechazar(id));
    }
}