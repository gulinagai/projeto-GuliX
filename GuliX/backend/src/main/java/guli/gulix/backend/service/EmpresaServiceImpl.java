package guli.gulix.backend.service;

import guli.gulix.backend.dto.EmpresaResponseDTO;
import guli.gulix.backend.dto.EmpresaUpdateDTO;
import guli.gulix.backend.entity.Empresa;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.mapper.EmpresaMapper;
import guli.gulix.backend.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    @RequiredArgsConstructor
    public class EmpresaServiceImpl implements EmpresaService {

        private final EmpresaMapper empresaMapper;
        private final EmpresaRepository empresaRepository;

        @Override
        public EmpresaResponseDTO getEmpresa() {
            Empresa empresa = empresaRepository.findFirstBy()
                    .orElseThrow(() ->
                            new RecursoNaoEncontradoException(
                                    "Empresa não encontrada"
                            )
                    );

            return empresaMapper.toDTO(empresa);
        }

    @Override
    public EmpresaResponseDTO updateEmpresa(EmpresaUpdateDTO dto) {
        Empresa empresa = empresaRepository.findFirstBy()
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Empresa não encontrada"
                        )
                );

        empresaMapper.updateFromDto(dto, empresa);

        return empresaMapper.toDTO(empresa);
    }

}
