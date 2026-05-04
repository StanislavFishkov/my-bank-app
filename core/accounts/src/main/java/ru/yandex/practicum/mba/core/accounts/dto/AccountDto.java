package ru.yandex.practicum.mba.core.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String login;
    private String name;
    private Integer balance;
    private LocalDate birthdate;
}