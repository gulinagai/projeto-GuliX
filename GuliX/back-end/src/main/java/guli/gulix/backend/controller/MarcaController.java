package guli.gulix.backend.controller;




import guli.gulix.backend.dto.MarcaRequestDTO;
import guli.gulix.backend.dto.MarcaResponseDTO;

import guli.gulix.backend.entity.Marca;

import guli.gulix.backend.mapper.MarcaMapper;

import guli.gulix.backend.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/marcas")
public class MarcaController {
    private final MarcaService marcaService;
    private final MarcaMapper marcaMapper;

    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> getListMarca() {

        List<Marca> marcas = marcaService.getListMarca();

        List<MarcaResponseDTO> response = marcas.stream().map(marcaMapper::toDTO).toList();
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{marcaId}")
    public ResponseEntity<MarcaResponseDTO> getMarcaById(@PathVariable("marcaId") Integer marcaId) {

        Marca marca = marcaService.getMarcaById(marcaId);

        return ResponseEntity.ok(marcaMapper.toDTO(marca));
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDTO> createNewMarca(@RequestBody MarcaRequestDTO marcaRequest) {

        Marca novaMarca = marcaService.createNewMarca(marcaRequest);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/marcas/" + novaMarca.getId().toString());

        MarcaResponseDTO response = marcaMapper.toDTO(novaMarca);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{marcaId}")
    public ResponseEntity<MarcaResponseDTO> updateMarcaById(@PathVariable("marcaId") Integer marcaId, @RequestBody MarcaRequestDTO marcaAtualizar) {

        Marca marca = marcaService.updateMarcaById(marcaId, marcaAtualizar);

        return ResponseEntity.ok(marcaMapper.toDTO(marca));

    }

    @DeleteMapping("/{marcaId}")
    public ResponseEntity<Void> deleteMarcaById(@PathVariable Integer marcaId) {

        marcaService.deleteMarcaById(marcaId);

        return ResponseEntity.noContent().build();

    }

}