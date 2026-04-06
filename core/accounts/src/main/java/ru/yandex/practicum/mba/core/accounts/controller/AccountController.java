package ru.yandex.practicum.mba.core.accounts.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    @GetMapping
    public String getAccount(@AuthenticationPrincipal Jwt jwt, Principal principal) {
        log.warn(jwt.getClaims().get("sub").toString());
        return principal.getName();
    }
}
