package ru.yandex.practicum.mba.core.accounts.error.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super(message);
    }
}

