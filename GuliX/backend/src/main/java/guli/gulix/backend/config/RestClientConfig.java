package guli.gulix.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient openRouteServiceRestClient() {
        return RestClient.builder()
                .baseUrl("https://api.heigit.org")
                .build();
    }

}
