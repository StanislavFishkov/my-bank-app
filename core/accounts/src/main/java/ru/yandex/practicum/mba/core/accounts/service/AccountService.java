package ru.yandex.practicum.mba.core.accounts.service;

import ru.yandex.practicum.mba.core.accounts.dto.AccountDto;
import ru.yandex.practicum.mba.core.accounts.dto.UpdateRequestAccountDto;
import ru.yandex.practicum.mba.core.accounts.security.UserContext;

public interface AccountService {
    AccountDto getOrCreateAccountByUserContext(UserContext userContext);

    AccountDto updateAccountBySubject(String subject, UpdateRequestAccountDto updateRequest);
}
