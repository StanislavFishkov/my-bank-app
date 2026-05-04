package ru.yandex.practicum.mba.ui.front.web.service;

import ru.yandex.practicum.mba.ui.front.web.dto.AccountDto;

import java.time.LocalDate;

public interface InternalGatewayService {
    AccountDto getAccount();

    AccountDto setNameAndBirthdate(String name, LocalDate birthdate);
}
