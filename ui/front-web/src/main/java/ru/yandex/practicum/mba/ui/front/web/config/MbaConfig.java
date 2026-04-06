package ru.yandex.practicum.mba.ui.front.web.config;

import io.netty.handler.logging.LogLevel;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
@EnableConfigurationProperties(MbaConfigProperties.class)
public class MbaConfig {

    @Bean
    public WebClient webClient(MbaConfigProperties mbaConfigProperties,
                               OAuth2AuthorizedClientManager  authorizedClientManager) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        // брать токен из текущей сессии
        oauth2.setDefaultOAuth2AuthorizedClient(true);

        HttpClient httpClient = HttpClient.create();
        if (mbaConfigProperties.clientWiretapEnabled()) {
            httpClient = httpClient.wiretap(
                    "reactor.netty.http.client.HttpClient",
                    LogLevel.DEBUG,
                    AdvancedByteBufFormat.TEXTUAL
            );
        }
        httpClient.warmup().block();

        return WebClient.builder()
                .baseUrl(mbaConfigProperties.apiUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .apply(oauth2.oauth2Configuration())
                .build();
    }
}
