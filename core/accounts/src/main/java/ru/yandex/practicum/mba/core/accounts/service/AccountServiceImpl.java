package ru.yandex.practicum.mba.core.accounts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.mba.core.accounts.dto.AccountDto;
import ru.yandex.practicum.mba.core.accounts.dto.UpdateRequestAccountDto;
import ru.yandex.practicum.mba.core.accounts.error.exception.NotFoundException;
import ru.yandex.practicum.mba.core.accounts.mapper.AccountMapper;
import ru.yandex.practicum.mba.core.accounts.model.Account;
import ru.yandex.practicum.mba.core.accounts.repository.AccountRepository;
import ru.yandex.practicum.mba.core.accounts.security.UserContext;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountDto getOrCreateAccountByUserContext(UserContext userContext) {
        log.debug("Get or create account for subject={}", userContext.subject());

        Account account = accountRepository.getAccountBySubject(userContext.subject())
                .map(existingAccount -> {
                    log.info("Account found for subject={}", userContext.subject());
                    return existingAccount;
                })
                .orElseGet(() -> createAccountByUserContext(userContext));

        return accountMapper.toDto(account);
    }

    @Override
    @Transactional
    public AccountDto updateAccountBySubject(String subject, UpdateRequestAccountDto updateRequest) {
        log.debug("Update account for subject={}, update request {}", subject,  updateRequest);

        Account account = accountRepository.getAccountBySubject(subject)
                    .orElseThrow(() -> new NotFoundException("Account doesn't exist for subject %s".formatted(subject)));

        accountMapper.update(account, updateRequest);
        account = accountRepository.save(account);
        log.info("Account updated for subject={}", subject);

        return accountMapper.toDto(account);
    }

    private Account createAccountByUserContext(UserContext userContext) {
        Account account = accountRepository.save(accountMapper.toEntity(userContext));

        log.info("Account created: id={}, subject={}, login={}",
                account.getId(), account.getSubject(), userContext.login());

        return account;
    }
}