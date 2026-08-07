package guli.gulix.backend.controller;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.dto.ItemCarrinhoRequestDTO;
import guli.gulix.backend.dto.ItemCarrinhoUpdateDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.service.CarrinhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<CarrinhoResponseDTO> buscarCarrinho(
            @AuthenticationPrincipal Usuario usuario
    ) {

        CarrinhoResponseDTO response = carrinhoService.buscarCarrinho(usuario.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/itens")
    public ResponseEntity<Void> adicionarItem(
            @AuthenticationPrincipal  Usuario usuario,
            @RequestBody ItemCarrinhoRequestDTO dto
            ) {
        carrinhoService.adicionarItem(usuario.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/itens/{itemId}")
    public ResponseEntity<Void> atualizarQuantidade (
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Integer itemId,
            @RequestBody ItemCarrinhoUpdateDTO dto
    ) {
        carrinhoService.atualizarQuantidade(usuario.getId(), itemId, dto);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/itens/{itemId}")
    public ResponseEntity<Void> removerItem (
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Integer itemId
    ) {
        carrinhoService.removerItem(usuario.getId(), itemId);
        return ResponseEntity.noContent().build();
    }
}
