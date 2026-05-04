package ru.yandex.practicum.mba.core.accounts.error.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
