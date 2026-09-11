package guli.gulix.backend.controller;

import guli.gulix.backend.dto.PoliticaFreteCreateDTO;
import guli.gulix.backend.dto.PoliticaFreteResponseDTO;
import guli.gulix.backend.dto.PoliticaFreteUpdateDTO;
import guli.gulix.backend.service.PoliticaFreteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/politicas-frete")
public class PoliticaFreteController {

    private final PoliticaFreteService politicaFreteService;

    @GetMapping
    ResponseEntity<List<PoliticaFreteResponseDTO>> getAllPoliticaFrete() {

        return ResponseEntity.ok().body(politicaFreteService.getAllPoliticaFrete());
    }

    @GetMapping("/{politicaFreteId}")
    ResponseEntity<PoliticaFreteResponseDTO> getPoliticaFreteById(@PathVariable("politicaFreteId") Integer politicaFreteId) {

        return ResponseEntity.ok().body(politicaFreteService.getPoliticaFreteById(politicaFreteId));
    }

    @PostMapping
    ResponseEntity<PoliticaFreteResponseDTO> createNewPoliticaFrete(@Valid @RequestBody PoliticaFreteCreateDTO dto) {

        PoliticaFreteResponseDTO novaPoliticaFrete = politicaFreteService.createNewPoliticaFrete(dto);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/politicaFretes/" + novaPoliticaFrete.id().toString());

        return ResponseEntity.ok().body(novaPoliticaFrete);
    }

    @PatchMapping("/{politicaFreteId}")
    ResponseEntity<PoliticaFreteResponseDTO> updatePoliticaFreteById(@PathVariable Integer politicaFreteId,@Valid @RequestBody PoliticaFreteUpdateDTO dto) {

        return ResponseEntity.ok().body(politicaFreteService.updatePoliticaFreteById(politicaFreteId, dto));
    }

    @DeleteMapping("/{politicaFreteId}")
    ResponseEntity<Void> deletePoliticaFreteById(@PathVariable Integer politicaFreteId) {

        politicaFreteService.deletePoliticaFreteById(politicaFreteId);

        return ResponseEntity.noContent().build();
    }


}
