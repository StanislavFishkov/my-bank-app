package ru.yandex.practicum.mba.ui.front.web.service;

import ru.yandex.practicum.mba.ui.front.web.dto.AccountDto;
import ru.yandex.practicum.mba.ui.front.web.dto.AccountShortDto;
import ru.yandex.practicum.mba.ui.front.web.dto.CashAction;

import java.time.LocalDate;
import java.util.List;

public interface InternalGatewayService {
    AccountDto getAccount();

    List<AccountShortDto> getTransferRecipients();

    AccountDto setNameAndBirthdate(String name, LocalDate birthdate);

    void processCash(CashAction action, Integer amount);

    void transferMoney(String recipientLogin, Integer amount);
}
