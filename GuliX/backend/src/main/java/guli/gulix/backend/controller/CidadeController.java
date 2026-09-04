package guli.gulix.backend.controller;

import guli.gulix.backend.dto.CidadeCreateDTO;
import guli.gulix.backend.dto.CidadeResponseDTO;
import guli.gulix.backend.dto.CidadeUpdateDTO;
import guli.gulix.backend.service.CidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @GetMapping
    ResponseEntity<List<CidadeResponseDTO>> getAllCidade() {

        return ResponseEntity.ok().body(cidadeService.getAllCidade());
    }

    @GetMapping("/{cidadeId}")
    ResponseEntity<CidadeResponseDTO> getCidadeById(@PathVariable("cidadeId") Integer cidadeId) {

        return ResponseEntity.ok().body(cidadeService.getCidadeById(cidadeId));
    }

    @PostMapping
    ResponseEntity<CidadeResponseDTO> createNewCidade(@Valid @RequestBody CidadeCreateDTO dto) {

        CidadeResponseDTO novaCidade = cidadeService.createNewCidade(dto);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/cidades/" + novaCidade.id().toString());

        return ResponseEntity.ok().body(cidadeService.createNewCidade(dto));
    }

    @PatchMapping("/{cidadeId}")
    ResponseEntity<CidadeResponseDTO> updateCidadeById(@PathVariable Integer cidadeId,@Valid @RequestBody CidadeUpdateDTO dto) {

        return ResponseEntity.ok().body(cidadeService.updateCidadeById(cidadeId, dto));
    }

    @DeleteMapping("/{cidadeId}")
    ResponseEntity<Void> deleteCidadeById(@PathVariable Integer cidadeId) {

        cidadeService.deleteCidadeById(cidadeId);

        return ResponseEntity.noContent().build();
    }


}
