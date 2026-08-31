package guli.gulix.backend.service;

import guli.gulix.backend.dto.EstoqueRequestDTO;
import guli.gulix.backend.dto.EstoqueRequestInventarioDTO;
import guli.gulix.backend.dto.EstoqueResponseDTO;
import guli.gulix.backend.entity.Estoque;
import guli.gulix.backend.entity.Produto;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.exception.RegraNegocioException;
import guli.gulix.backend.mapper.EstoqueMapper;
import guli.gulix.backend.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final EstoqueMapper estoqueMapper;

    @Override
    public List<EstoqueResponseDTO> getAllEstoque() {
        return estoqueRepository.findAll().stream().map(
                item -> {
                    EstoqueResponseDTO response = estoqueMapper.toDTO(item);
                    response.setEstoqueDisponivel(calculaEstoqueDisponivel(item.getEstoqueTotal(), item.getEstoqueReservado()));
                    return response;
                }
        ).toList();
    }

    @Override
    public EstoqueResponseDTO getEstoqueByProdutoId(Integer produtoId) {

        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(
                        ()-> new RecursoNaoEncontradoException(
                                "Estoque do produto de id " + produtoId + " não encontrado"
                        )
                );

        EstoqueResponseDTO response = estoqueMapper.toDTO(estoque);
        response.setEstoqueDisponivel(calculaEstoqueDisponivel(estoque.getEstoqueTotal(), estoque.getEstoqueReservado()));

        return response;
    }

    @Override
    public void createNewEstoque(Produto produto) {
        Estoque estoque = new Estoque();

        estoque.setProduto(produto);
        estoque.setEstoqueReservado(0);
        estoque.setEstoqueTotal(0);

        estoqueRepository.save(estoque);
    }

    @Override
    public EstoqueResponseDTO adicionarEstoque(Integer produtoId, EstoqueRequestDTO estoqueRequest) {

        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(
                        ()-> new RecursoNaoEncontradoException(
                                "Estoque do produto de id " + produtoId + " não encontrado"
                        )
                );

        estoque.setEstoqueTotal(calculaSomaEstoque(estoque.getEstoqueTotal(), estoqueRequest.quantidade()));


        EstoqueResponseDTO response = estoqueMapper.toDTO(estoque);
        response.setEstoqueDisponivel(calculaEstoqueDisponivel(estoque.getEstoqueTotal(), estoque.getEstoqueReservado()));

        return response;
    }


    @Override
    public EstoqueResponseDTO removerEstoque(Integer produtoId, EstoqueRequestDTO estoqueRequest) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(
                        ()-> new RecursoNaoEncontradoException(
                                "Estoque do produto de id " + produtoId + " não encontrado"
                        )
                );

        estoque.setEstoqueTotal(calculaSubtracaoEstoque(estoque.getEstoqueTotal(), estoqueRequest.quantidade(), estoque.getEstoqueReservado()));

        EstoqueResponseDTO response = estoqueMapper.toDTO(estoque);
        response.setEstoqueDisponivel(calculaEstoqueDisponivel(estoque.getEstoqueTotal(), estoque.getEstoqueReservado()));

        return response;
    }


    @Override
    public EstoqueResponseDTO realizarInventario(Integer produtoId, EstoqueRequestInventarioDTO estoqueRequest) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(
                        ()-> new RecursoNaoEncontradoException(
                                "Estoque do produto de id " + produtoId + " não encontrado"
                        )
                );

        if(estoque.getEstoqueReservado() > estoqueRequest.quantidade()) throw new RegraNegocioException("A quantidade informada no inventário é menor do que a quantidade em reserva");

        estoque.setEstoqueTotal(estoqueRequest.quantidade());

        EstoqueResponseDTO response = estoqueMapper.toDTO(estoque);
        response.setEstoqueDisponivel(calculaEstoqueDisponivel(estoque.getEstoqueTotal(), estoque.getEstoqueReservado()));

        return response;
    }

    @Override
    public Integer getQuantidadeDisponivel(Integer produtoId) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(
                        ()-> new RecursoNaoEncontradoException(
                                "Estoque do produto de id " + produtoId + " não encontrado"
                        )
                );

        return calculaEstoqueDisponivel(estoque.getEstoqueTotal(), estoque.getEstoqueReservado());
    }

    public void setQuantidadeReservada(Integer produtoId, Integer quantidadeAReservar, String operacao) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(
                        ()-> new RecursoNaoEncontradoException(
                                "Estoque do produto de id " + produtoId + " não encontrado"
                        )
                );

        if("soma".equals(operacao)) {
            reservaProduto(estoque, quantidadeAReservar);
        } else if("subtracao".equals(operacao)) {
            removeReservaProduto(estoque, quantidadeAReservar);
        }

    }

    private Integer calculaSomaEstoque(Integer estoqueTotal, Integer estoqueASomar) {
        return estoqueTotal + estoqueASomar;
    }

    private Integer calculaSubtracaoEstoque(Integer estoqueTotal, Integer estoqueASubtrair, Integer estoqueReservado) {

        if(estoqueASubtrair <= estoqueTotal) {
            if((estoqueTotal - estoqueASubtrair) >= estoqueReservado)
                return estoqueTotal - estoqueASubtrair;
            else {
                throw new RegraNegocioException("A quantidade total após a subtração é menor do que a quantidade reservada");
            }
        }
        else {
            throw new RegraNegocioException("A quantidade solicitada para subtração é maior do que a quantidade em estoque");
        }
    }

    private Integer calculaEstoqueDisponivel(Integer estoqueTotal, Integer estoqueReservado) {
        return estoqueTotal - estoqueReservado;
    }

    private void reservaProduto(Estoque estoque, Integer quantidadeAReservar) {
        estoque.setEstoqueReservado(estoque.getEstoqueReservado() + quantidadeAReservar);
    }

    private void removeReservaProduto(Estoque estoque, Integer quantidadeAReservar) {
        if (estoque.getEstoqueReservado() - quantidadeAReservar >= 0) {
            estoque.setEstoqueReservado(estoque.getEstoqueReservado() - quantidadeAReservar);
        }
    }
}
