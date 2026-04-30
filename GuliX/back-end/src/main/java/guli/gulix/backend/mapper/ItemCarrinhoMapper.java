package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.ItemCarrinhoResponseDTO;
import guli.gulix.backend.entity.ItemCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {
    @Mapping(source = "produto.id", target = "produtoId")
    @Mapping(source = "produto.nome", target = "nomeProduto")
    @Mapping(source = "produto.preco", target = "preco")
    ItemCarrinhoResponseDTO toDTO(ItemCarrinho item);
}


// mas por exemplo, para converter Carrinho -> Carrinho DTO, precisa do CarrinhoMapper,
// e uma das conversões, como é uma entidade também, e essa entidade tem outros atributos,
// precisa do ItemCarrinhoMapper, para fazer ItemCarrinho -> ItemCarrinhoDTO, mas ele precisa fazer um get
// desses ItemCarrinho de dentro do carrinho específico,
// para só então converter e setar para o dto, minha dúvida é, como é possível esse get? ele busca do banco

// sim, é feito consulta no banco, mas quem busca é o Hibernate (JPA), O MapStruct só usa o que já está em memória



// Vamos seguir o fluxo REAL do seu código
// 1. Você chama:
//        carrinhoRepository.findByUsuarioId(usuarioId)
// 2. O Hibernate faz:
//
//         Query no banco:
//
// SELECT * FROM carrinho WHERE usuario_id = ?
//        3. Resultado:
//
// Você recebe um objeto:
//
// Carrinho carrinho;
// Agora vem o ponto chave
//
//Esse objeto carrinho tem:
//
//private List<ItemCarrinho> itens;
//
//  Mas essa lista ainda pode NÃO estar carregada
//
//  4. Quando o getItens() acontece?
//
// Quando você faz:
//
//        carrinhoMapper.toDTO(carrinho);
//
// internamente ele faz:
//
//        carrinho.getItens() //  AQUI
//️
// 5. O que acontece nesse momento?
//         Se estiver LAZY (padrão):
//
// O Hibernate dispara:
//
// SELECT * FROM item_carrinho WHERE carrinho_id = ?
//
//         Ou seja:
//
//        ✔ O getItens() faz a query automaticamente
//
// 6. Então respondendo sua dúvida
//
//  “como ele faz o get?”
//
//         Assim:
//
// MapStruct chama → carrinho.getItens()
// Hibernate intercepta → faz query no banco
// Retorna lista → MapStruct usa
// 7. E depois disso?
//
// MapStruct faz:
//
//        for (ItemCarrinho item : carrinho.getItens()) {
//              itemCarrinhoMapper.toDTO(item);
//         }


// então, basicamente o ItemCarrinhoResponseDTO será parte do json de response também, uma vez que um dos atributos de response
// no ResponseDTO é private List<ItemCarrinhoResponseDTO> itens;