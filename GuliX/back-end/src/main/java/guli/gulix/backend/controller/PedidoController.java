package guli.gulix.backend.controller;

import guli.gulix.backend.dto.PedidoResponseDTO;
import guli.gulix.backend.dto.PedidoUpdateStatusDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    // usuario - listar pedidos do usuario
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> getListPedidos(
            @AuthenticationPrincipal Usuario usuario
            ) {
        return ResponseEntity.ok(pedidoService.getListPedidos(usuario));
    }

    // usuario - listar um pedido do usuario baseado no id
    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoResponseDTO> getPedidoById(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable("pedidoId") Integer pedidoId
    ) {
        return ResponseEntity.ok(pedidoService.getPedidoById(usuario, pedidoId));
    }


    // usuario - novo pedido
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> createNewPedido(
            @AuthenticationPrincipal Usuario usuario
    ) {

        PedidoResponseDTO response = pedidoService.createNewPedido(usuario);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Location", "/api/v1/pedidos/" + response.getId().toString());

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    // usuario cancelar (status)
    @PatchMapping("/{pedidoId}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancelPedido(
            @PathVariable("pedidoId") Integer pedidoId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        PedidoResponseDTO response = pedidoService.cancelPedido(pedidoId, usuario);

        return ResponseEntity.ok(response);
    }


    // admin - listar todos os pedidos de todos
    @GetMapping("/admin")
    public ResponseEntity<List<PedidoResponseDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    // admin atualizar (status)
    @PatchMapping("/admin/{pedidoId}/status")
    public ResponseEntity<PedidoResponseDTO> updateStatusPedido(
            @PathVariable("pedidoId") Integer pedidoId,
            @RequestBody PedidoUpdateStatusDTO dto
            ) {
        return ResponseEntity.ok(pedidoService.updateStatusPedido(pedidoId, dto));
    }
}
