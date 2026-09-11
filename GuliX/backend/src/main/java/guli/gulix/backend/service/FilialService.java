package guli.gulix.backend.service;

import guli.gulix.backend.dto.FilialResponseDTO;
import guli.gulix.backend.dto.FilialUpdateDTO;
import jakarta.validation.Valid;

public interface FilialService {
    FilialResponseDTO getFilial();

    FilialResponseDTO updateFilial(@Valid FilialUpdateDTO dto);
}
