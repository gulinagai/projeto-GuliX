package guli.gulix.backend.controller;


import guli.gulix.backend.dto.EmpresaResponseDTO;
import guli.gulix.backend.dto.EmpresaUpdateDTO;
import guli.gulix.backend.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    ResponseEntity<EmpresaResponseDTO> getEmpresa() {

        return ResponseEntity.ok().body(empresaService.getEmpresa());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{empresaId}")
    ResponseEntity<EmpresaResponseDTO> updateEmpresa(@Valid @RequestBody EmpresaUpdateDTO dto) {

        return ResponseEntity.ok().body(empresaService.updateEmpresa(dto));
    }

}
