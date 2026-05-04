package ru.yandex.practicum.mba.core.accounts.error.exception;

public class ConflictDataException extends RuntimeException {
    public ConflictDataException(String message) {
        super(message);
    }
}