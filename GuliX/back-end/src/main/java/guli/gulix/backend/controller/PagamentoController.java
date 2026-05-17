package guli.gulix.backend.controller;

import guli.gulix.backend.dto.PagamentoRequestDTO;
import guli.gulix.backend.dto.PagamentoResponseDTO;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    // criar pagamento
    // fica dentro do PedidoServiceImpl

    // buscar pagamento
    @GetMapping("/{pagamentoId}")
    public ResponseEntity<PagamentoResponseDTO> getPagamentoById(
            @PathVariable Integer pagamentoId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(pagamentoService.getPagamentoById(pagamentoId, usuario));
    }

    // confirmar pagamento
    @PatchMapping("/{pagamentoId}/confirmar")
    public ResponseEntity<PagamentoResponseDTO> confirmarPagamento(
            @PathVariable Integer pagamentoId,
            @AuthenticationPrincipal Usuario usuario
    ) {
        return ResponseEntity.ok(
                pagamentoService.confirmarPagamento(pagamentoId, usuario)
        );
    }
}