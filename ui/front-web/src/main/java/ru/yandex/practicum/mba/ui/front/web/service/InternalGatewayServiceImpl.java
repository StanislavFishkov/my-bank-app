package ru.yandex.practicum.mba.ui.front.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalGatewayServiceImpl implements InternalGatewayService {
    private final WebClient webClient;

    @Override
    public String getAccount() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/account").build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
