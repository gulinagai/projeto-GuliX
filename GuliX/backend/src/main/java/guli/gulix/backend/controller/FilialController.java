package guli.gulix.backend.controller;


import guli.gulix.backend.dto.FilialResponseDTO;
import guli.gulix.backend.dto.FilialUpdateDTO;
import guli.gulix.backend.service.FilialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/filiais")
public class FilialController {

    private final FilialService filialService;

    @GetMapping
    ResponseEntity<FilialResponseDTO> getFilial() {

        return ResponseEntity.ok().body(filialService.getFilial());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{filialId}")
    ResponseEntity<FilialResponseDTO> updateFilial(@Valid @RequestBody FilialUpdateDTO dto) {

        return ResponseEntity.ok().body(filialService.updateFilial(dto));
    }

}
