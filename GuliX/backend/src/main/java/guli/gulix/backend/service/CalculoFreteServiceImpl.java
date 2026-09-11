package guli.gulix.backend.service;

import guli.gulix.backend.entity.Empresa;
import guli.gulix.backend.entity.Filial;
import guli.gulix.backend.exception.RecursoNaoEncontradoException;
import guli.gulix.backend.gateway.openrouteservice.OpenRouteServiceGateway;
import guli.gulix.backend.gateway.openrouteservice.dto.Rota;
import guli.gulix.backend.geographic.Coordenada;
import guli.gulix.backend.repository.EmpresaRepository;
import guli.gulix.backend.repository.FilialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@Service
public class CalculoFreteServiceImpl implements CalculoFreteService {

    private final FilialRepository filialRepository;
    private final OpenRouteServiceGateway openRouteServiceGateway;
    private final PoliticaFreteService politicaFreteService;
    private final EmpresaRepository empresaRepository;


    @Override
    public BigDecimal calcularFrete(Coordenada coordenadaDestino, String siglaEstado) {

        Filial filial = filialRepository.findFirstBy()
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Filial não encontrada"
                        )
                );

        Coordenada coordenadaOrigem = new Coordenada(
                filial.getEndereco().getLatitude().doubleValue(),
                filial.getEndereco().getLongitude().doubleValue()
        );

        Rota response = openRouteServiceGateway.calcularRota(coordenadaOrigem, coordenadaDestino);

        Empresa empresa = empresaRepository.findFirstBy()
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Empresa não encontrada"
                        )
                );

        BigDecimal valorPorKm = empresa.getValorPorKmFrete();
        BigDecimal valorBaseEstado = politicaFreteService.getValorBasePorSiglaEstado(siglaEstado);
        BigDecimal distanciaPercorridaEmKm = response.distanciaMetros().divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);

        BigDecimal frete = valorBaseEstado.add(valorPorKm.multiply(distanciaPercorridaEmKm));

        return frete;
    }
}
