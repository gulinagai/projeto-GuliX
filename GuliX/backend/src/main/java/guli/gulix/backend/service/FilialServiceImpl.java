package guli.gulix.backend.service;

import guli.gulix.backend.dto.FilialResponseDTO;
import guli.gulix.backend.dto.FilialUpdateDTO;
import guli.gulix.backend.entity.Filial;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.FilialMapper;
import guli.gulix.backend.repository.FilialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilialServiceImpl implements FilialService {

    private final FilialMapper filialMapper;
    private final FilialRepository filialRepository;

    @Override
    public FilialResponseDTO getFilial() {
        Filial filial = filialRepository.findFirstBy()
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Filial não encontrada"
                        )
                );

        return filialMapper.toDTO(filial);
    }

    @Override
    public FilialResponseDTO updateFilial(FilialUpdateDTO dto) {
        Filial filial = filialRepository.findFirstBy()
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Filial não encontrada"
                        )
                );

        filialMapper.updateFromDto(dto, filial);

        return filialMapper.toDTO(filial);
    }

}
