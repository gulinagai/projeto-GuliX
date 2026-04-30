package guli.gulix.backend.controller;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.dto.ItemCarrinhoRequestDTO;
import guli.gulix.backend.dto.ItemCarrinhoUpdateDTO;
import guli.gulix.backend.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponseDTO> buscarCarrinho(@RequestParam Integer usuarioId) {

        CarrinhoResponseDTO response = carrinhoService.buscarCarrinho(usuarioId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/itens")
    public ResponseEntity<Void> adicionarItem(
            @RequestParam Integer usuarioId,
            @RequestBody ItemCarrinhoRequestDTO dto
            ) {
        carrinhoService.adicionarItem(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/itens/{itemId}")
    public ResponseEntity<Void> atualizarQuantidade (
            @RequestParam Integer usuarioId,
            @PathVariable Integer itemId,
            @RequestBody ItemCarrinhoUpdateDTO dto
    ) {
        carrinhoService.atualizarQuantidade(usuarioId, itemId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/itens/{itemId}")
    public ResponseEntity<Void> removerItem (
            @RequestParam Integer usuarioId,
            @PathVariable Integer itemId
    ) {
        carrinhoService.removerItem(usuarioId, itemId);
        return ResponseEntity.noContent().build();
    }
}
