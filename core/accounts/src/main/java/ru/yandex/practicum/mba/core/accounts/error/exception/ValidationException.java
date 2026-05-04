package ru.yandex.practicum.mba.core.accounts.error.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
