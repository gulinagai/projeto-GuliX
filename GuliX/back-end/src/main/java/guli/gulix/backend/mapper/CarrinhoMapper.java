package guli.gulix.backend.mapper;

import guli.gulix.backend.dto.CarrinhoResponseDTO;
import guli.gulix.backend.entity.Carrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//  @Mapper indica que essa interface é um mapper e que precisa de uma implementação disso como  CarrinhoMapperImpl
// componentModel = "spring" diz que isso deve ser um bean gerenciado pelo Spring Context, e que pode ser injetável por DI em um Service
// uses = ItemCarrinhoMapper.class diz que, se precisar converter ItemCarrinho, usa esse mapper aqui (ItemCarrinhoMapper.class)
// em outras palavras, se existir um mapeamento necessário entre tipos, e esse mapper souber fazer, utilize-o.
// uses basicamente permite usar outro mapper automaticamente, como o mapeamento de Carrinho para CarrinhoDTO vai precisar de ItemCarrinho também
// o Mapstruct oferece essa ferramenta de conversão automática usando outro mapper

@Mapper(componentModel = "spring", uses = ItemCarrinhoMapper.class)
public interface CarrinhoMapper {


    // quando o campo existe no DTO mas eu não quero que o mapper faça nenhum de conversão, uso:
    @Mapping(target = "total", ignore = true) // porque isso é calculado e inserido manualmente no service
    CarrinhoResponseDTO toDTO(Carrinho carrinho);
}


