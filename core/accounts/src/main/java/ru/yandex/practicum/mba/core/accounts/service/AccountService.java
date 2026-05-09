package ru.yandex.practicum.mba.core.accounts.service;

import ru.yandex.practicum.mba.core.accounts.dto.AccountDto;
import ru.yandex.practicum.mba.core.accounts.dto.AccountShortDto;
import ru.yandex.practicum.mba.core.accounts.dto.UpdateRequestAccountDto;
import ru.yandex.practicum.mba.core.accounts.security.UserContext;

import java.util.List;

public interface AccountService {
    AccountDto getOrCreateAccountByUserContext(UserContext userContext);

    List<AccountShortDto> getRecipients(String subject);

    AccountDto updateAccountBySubject(String subject, UpdateRequestAccountDto updateRequest);
}
