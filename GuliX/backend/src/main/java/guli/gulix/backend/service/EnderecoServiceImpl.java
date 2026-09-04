package guli.gulix.backend.service;

import guli.gulix.backend.dto.EnderecoCreateDTO;
import guli.gulix.backend.dto.EnderecoResponseDTO;
import guli.gulix.backend.dto.EnderecoUpdateDTO;
import guli.gulix.backend.entity.Cidade;
import guli.gulix.backend.entity.Endereco;
import guli.gulix.backend.entity.Usuario;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.gateway.openrouteservice.OpenRouteServiceGateway;
import guli.gulix.backend.geographic.Coordenada;
import guli.gulix.backend.geographic.GeradorCoordenadas;
import guli.gulix.backend.mapper.EnderecoMapper;
import guli.gulix.backend.repository.CidadeRepository;
import guli.gulix.backend.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService{

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final CidadeRepository cidadeRepository;
    private final OpenRouteServiceGateway openRouteServiceGateway;

    @Override
    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> getListEnderecos(Usuario usuarioLogado) {  // passar usuarioId
            return enderecoRepository.findByUsuarioId(usuarioLogado.getId()).stream().map(enderecoMapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EnderecoResponseDTO getEnderecoById(Integer enderecoId, Usuario usuarioLogado) {
            Endereco endereco = enderecoRepository.findById(enderecoId)
                    .orElseThrow(()->
                            new RecursoNaoEncontradoException(
                                    "Endereço com id " + enderecoId + " não encontrado"
                            ));


            validarDono(endereco, usuarioLogado);

            return enderecoMapper.toDTO(endereco);
    }

    @Override
    public EnderecoResponseDTO createNewEndereco(EnderecoCreateDTO enderecoRequest, Usuario usuarioLogado) { // adicionar Usuario usuarioLogado
        Endereco endereco = enderecoMapper.toEntity(enderecoRequest);

         endereco.setUsuario(usuarioLogado);

         validaPrimeiroEndereco(usuarioLogado.getId(), endereco);

         // gera coordenadas

        Coordenada preCoordenada = buscaCoordenada(enderecoRequest.getCidade());
        GeradorCoordenadas geradorCoordenadas = new GeradorCoordenadas();
        Coordenada novaCoordenadaAleatoria = geradorCoordenadas.gerar(preCoordenada.latitude(), preCoordenada.longitude());

        // coordenada certa, precisa persistir no banco
        Coordenada coordenadaSnap = openRouteServiceGateway.snap(novaCoordenadaAleatoria);

        endereco.setLatitude(BigDecimal.valueOf(coordenadaSnap.latitude()));
        endereco.setLongitude(BigDecimal.valueOf(coordenadaSnap.longitude()));

        return enderecoMapper.toDTO(enderecoRepository.save(endereco));

    }

    private Coordenada buscaCoordenada(String cidade) {
        Cidade cidadePersistida = cidadeRepository.findByNome(cidade)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cidade não encontrada ou não cadastrada"
                ));

        return new Coordenada(cidadePersistida.getLatitudeCentral().doubleValue(), cidadePersistida.getLongitudeCentral().doubleValue());
    }

    @Override
    public void deleteEnderecoById(Integer enderecoId, Usuario usuarioLogado) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Endereço com id " + enderecoId + " não encontrado"
                        ));

        validarDono(endereco, usuarioLogado);

        enderecoRepository.delete(endereco);
    }

    @Override
    public EnderecoResponseDTO updateEnderecoById(Integer enderecoId, EnderecoUpdateDTO enderecotualizar, Usuario usuarioLogado) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Endereço com id " + enderecoId + " não encontrado"
                        ));

        validarDono(endereco, usuarioLogado);

        enderecoMapper.updateFromDto(enderecotualizar, endereco);

        return enderecoMapper.toDTO(endereco);
    }

    @Override
    public void updateEnderecoPrincipalById(Integer enderecoId, Usuario usuarioLogado) {
        Endereco enderecoExiste = enderecoRepository.findById(enderecoId)
                .orElseThrow(()->
                        new RecursoNaoEncontradoException(
                                "Endereço com id " + enderecoId + " não encontrado"
                        ));

        validarDono(enderecoExiste, usuarioLogado);

        List<Endereco> listaEnderecos = enderecoRepository.findByUsuarioId(usuarioLogado.getId());

        limpaPrincipal(listaEnderecos);

        enderecoExiste.setPrincipal(true);
    }

    public void limpaPrincipal(List<Endereco> listaEnderecos) {
        for (Endereco itemEndereco: listaEnderecos) {
                itemEndereco.setPrincipal(false);
        }
    }

    private void validaPrimeiroEndereco(Integer usuarioId, Endereco endereco) {
        List<Endereco> enderecos = enderecoRepository.findByUsuarioId(usuarioId);

        if (enderecos.isEmpty()) {
            endereco.setPrincipal(true);
        }
    }

    private void validarDono(Endereco endereco, Usuario usuarioLogado) {
        if(!endereco.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new RegraNegocioException("Acesso negado");
        }
    }

}
