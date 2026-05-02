package guli.gulix.backend.controller;

import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;
import guli.gulix.backend.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> getListEnderecos() {
        return ResponseEntity.ok(enderecoService.getListEnderecos());
    }

    @GetMapping("/{enderecoId}")
    public ResponseEntity<EnderecoResponseDTO> getEnderecoById(@PathVariable("enderecoId") Integer enderecoId) {
        return ResponseEntity.ok(enderecoService.getEnderecoById(enderecoId));
    }

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> createNewEndereco(@RequestBody EnderecoCreateDTO enderecoRequest) { // adicionar @AuthenticationPrincipal Usuario usuarioLogado quando implementar autenticação

        EnderecoResponseDTO novoEndereco = enderecoService.createNewEndereco(enderecoRequest); // passar usuarioLogado também

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/enderecos/" + novoEndereco.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(novoEndereco);
    }


    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Void> deleteEnderecoById(@PathVariable("enderecoId") Integer enderecoId) {
        enderecoService.deleteEnderecoById(enderecoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<EnderecoResponseDTO> updateEnderecoById(@PathVariable("enderecoId") Integer enderecoId, @RequestBody EnderecoUpdateDTO enderecotualizar) {

        EnderecoResponseDTO enderecoAtualizado = enderecoService.updateEnderecoById(enderecoId, enderecotualizar);

        return ResponseEntity.ok(enderecoAtualizado);
    }


    @PatchMapping("/{enderecoId}/principal")
    public ResponseEntity<Void> updateEnderecoPrincipalById(
            @PathVariable("enderecoId") Integer enderecoId
    ) {

        enderecoService.updateEnderecoPrincipalById(enderecoId);

        return ResponseEntity.noContent().build();
    }


}
