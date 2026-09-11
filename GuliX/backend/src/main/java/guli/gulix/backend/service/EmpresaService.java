package guli.gulix.backend.service;

import guli.gulix.backend.dto.EmpresaResponseDTO;
import guli.gulix.backend.dto.EmpresaUpdateDTO;
import java.util.List;

public interface EmpresaService {

    EmpresaResponseDTO getEmpresa();

    EmpresaResponseDTO updateEmpresa(EmpresaUpdateDTO dto);

}
