package ru.yandex.practicum.mba.core.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.mba.core.accounts.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> getAccountBySubject(String subject);
}