package ru.yandex.practicum.mba.core.accounts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.mba.core.accounts.dto.AccountDto;
import ru.yandex.practicum.mba.core.accounts.dto.AccountShortDto;
import ru.yandex.practicum.mba.core.accounts.dto.UpdateRequestAccountDto;
import ru.yandex.practicum.mba.core.accounts.service.AccountService;
import ru.yandex.practicum.mba.core.security.user.UserContext;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('accounts.self.read')")
    public AccountDto getMyAccount(UserContext userContext) {
        log.trace("GET /accounts/me with subject {}", userContext.subject());
        return accountService.getOrCreateAccountByUserContext(userContext);
    }

    @GetMapping("/recipients")
    @PreAuthorize("hasAuthority('accounts.recipients.read')")
    public List<AccountShortDto> getRecipients(UserContext userContext) {
        log.trace("GET /accounts/recipients with subject {}", userContext.subject());
        return accountService.getRecipients(userContext.subject());
    }

    @PatchMapping("/me")
    @PreAuthorize("hasAuthority('accounts.self.write')")
    public AccountDto updateMyAccount(UserContext userContext,
                                      @Valid @RequestBody UpdateRequestAccountDto updateRequest) {
        log.trace("PATCH /accounts/me with subject {}, update request {}", userContext.subject(), updateRequest);
        return accountService.updateAccountBySubject(userContext.subject(), updateRequest);
    }
}
