package guli.gulix.backend.controller;

import guli.gulix.backend.dto.EstadoCreateDTO;
import guli.gulix.backend.dto.EstadoResponseDTO;
import guli.gulix.backend.dto.EstadoUpdateDTO;
import guli.gulix.backend.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/estados")
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    ResponseEntity<List<EstadoResponseDTO>> getAllEstado() {

        return ResponseEntity.ok().body(estadoService.getAllEstado());
    }

    @GetMapping("/{estadoId}")
    ResponseEntity<EstadoResponseDTO> getEstadoById(@PathVariable("estadoId") Integer estadoId) {

        return ResponseEntity.ok().body(estadoService.getEstadoById(estadoId));
    }

    @PostMapping
    ResponseEntity<EstadoResponseDTO> createNewEstado(@RequestBody EstadoCreateDTO dto) {

        EstadoResponseDTO novoEstado = estadoService.createNewEstado(dto);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/estados/" + novoEstado.id().toString());

        return ResponseEntity.ok().body(estadoService.createNewEstado(dto));
    }

    @PatchMapping("/{estadoId}")
    ResponseEntity<EstadoResponseDTO> updateEstadoById(@PathVariable Integer estadoId, @RequestBody EstadoUpdateDTO dto) {

        return ResponseEntity.ok().body(estadoService.updateEstadoById(estadoId, dto));
    }

    @DeleteMapping("/{estadoId}")
    ResponseEntity<Void> deleteEstadoById(@PathVariable Integer estadoId) {

        estadoService.deleteEstadoById(estadoId);

        return ResponseEntity.noContent().build();
    }


}
