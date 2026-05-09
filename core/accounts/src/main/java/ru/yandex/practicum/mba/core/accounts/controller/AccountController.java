package ru.yandex.practicum.mba.core.accounts.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.mba.core.accounts.dto.AccountDto;
import ru.yandex.practicum.mba.core.accounts.dto.AccountShortDto;
import ru.yandex.practicum.mba.core.accounts.dto.UpdateRequestAccountDto;
import ru.yandex.practicum.mba.core.accounts.security.UserContextMapper;
import ru.yandex.practicum.mba.core.accounts.service.AccountService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final UserContextMapper userContextMapper;

    @GetMapping("/me")
    public AccountDto getMyAccount(@AuthenticationPrincipal Jwt jwt) {
        log.trace("GET /accounts/me with subject {}", jwt.getSubject());
        return accountService.getOrCreateAccountByUserContext(userContextMapper.from(jwt));
    }

    @GetMapping("/recipients")
    public List<AccountShortDto> getRecipients(@AuthenticationPrincipal Jwt jwt) {
        log.trace("GET /accounts/recipients with subject {}", jwt.getSubject());
        return accountService.getRecipients(jwt.getSubject());
    }

    @PatchMapping("/me")
    public AccountDto updateMyAccount(@AuthenticationPrincipal Jwt jwt,
                                      @Valid @RequestBody UpdateRequestAccountDto updateRequest) {
        log.trace("PATCH /accounts/me with subject {}, update request {}", jwt.getSubject(), updateRequest);
        return accountService.updateAccountBySubject(jwt.getSubject(), updateRequest);
    }

    @PostMapping("/me")
    public AccountDto postMyAccount(@RequestBody String raw) {
        log.info("RAW = {}", raw);
        return null;
    }
}
