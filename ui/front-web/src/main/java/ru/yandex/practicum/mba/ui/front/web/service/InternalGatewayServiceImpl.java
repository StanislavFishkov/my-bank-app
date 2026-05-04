package ru.yandex.practicum.mba.ui.front.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mba.ui.front.web.dto.AccountDto;
import ru.yandex.practicum.mba.ui.front.web.dto.UpdateRequestAccountDto;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalGatewayServiceImpl implements InternalGatewayService {
    private final WebClient webClient;

    @Override
    public AccountDto getAccount() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/account/me").build())
                .retrieve()
                .bodyToMono(AccountDto.class)
                .block();
    }

    @Override
    @SneakyThrows
    public AccountDto setNameAndBirthdate(String name, LocalDate birthdate) {
        UpdateRequestAccountDto updateRequest = UpdateRequestAccountDto.builder()
                .name(name)
                .birthdate(birthdate)
                .build();

        return webClient
                .patch()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/account/me").build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .retrieve()
                .bodyToMono(AccountDto.class)
                .block();
    }
}
