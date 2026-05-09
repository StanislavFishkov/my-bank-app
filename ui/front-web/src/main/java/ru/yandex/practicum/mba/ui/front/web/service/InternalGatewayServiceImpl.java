package ru.yandex.practicum.mba.ui.front.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yandex.practicum.mba.ui.front.web.dto.AccountDto;
import ru.yandex.practicum.mba.ui.front.web.dto.AccountShortDto;
import ru.yandex.practicum.mba.ui.front.web.dto.CashAction;
import ru.yandex.practicum.mba.ui.front.web.dto.UpdateRequestAccountDto;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalGatewayServiceImpl implements InternalGatewayService {
    private final WebClient webClient;

    @Override
    public AccountDto getAccount() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/accounts/me").build())
                .retrieve()
                .bodyToMono(AccountDto.class)
                .block();
    }

    @Override
    public List<AccountShortDto> getTransferRecipients() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/accounts/recipients").build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<AccountShortDto>>() {})
                .block();
    }

    @Override
    public AccountDto setNameAndBirthdate(String name, LocalDate birthdate) {
        UpdateRequestAccountDto updateRequest = UpdateRequestAccountDto.builder()
                .name(name)
                .birthdate(birthdate)
                .build();

        return webClient
                .patch()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/accounts/me").build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .retrieve()
                .bodyToMono(AccountDto.class)
                .block();
    }

    @Override
    public void processCash(CashAction action, Integer amount) {

    }

    @Override
    public void transferMoney(String recipientLogin, Integer amount) {

    }
}
