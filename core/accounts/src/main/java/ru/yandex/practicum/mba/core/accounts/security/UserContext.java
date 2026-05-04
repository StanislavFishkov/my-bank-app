package ru.yandex.practicum.mba.core.accounts.security;

public record UserContext(String subject, String login, String name, String email) {
}