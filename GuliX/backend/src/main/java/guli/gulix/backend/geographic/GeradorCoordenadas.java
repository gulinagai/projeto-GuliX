package guli.gulix.backend.geographic;

import java.util.concurrent.ThreadLocalRandom;

public class GeradorCoordenadas {

    private static final double RAIO_TERRA_KM = 6371.0;

    public Coordenada gerar(
            double latitudeCentral,
            double longitudeCentral,
            double raioKm
    ) {

        double angulo = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);

        double distancia = raioKm * Math.sqrt(
                ThreadLocalRandom.current().nextDouble()
        );

        double latitudeRad = Math.toRadians(latitudeCentral);

        double deltaLatitude = distancia * Math.cos(angulo);
        double deltaLongitude = distancia * Math.sin(angulo);

        double novaLatitude = latitudeCentral
                + Math.toDegrees(deltaLatitude / RAIO_TERRA_KM);

        double novaLongitude = longitudeCentral
                + Math.toDegrees(
                deltaLongitude /
                        (RAIO_TERRA_KM * Math.cos(latitudeRad))
        );

        return new Coordenada(novaLatitude, novaLongitude);
    }
}