package ru.yandex.practicum.mba.core.security.user;

public record UserContext(String subject, String login, String name, String email) {
}