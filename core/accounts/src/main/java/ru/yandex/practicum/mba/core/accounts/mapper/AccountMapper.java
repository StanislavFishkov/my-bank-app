package ru.yandex.practicum.mba.core.accounts.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.yandex.practicum.mba.core.accounts.dto.AccountDto;
import ru.yandex.practicum.mba.core.accounts.dto.UpdateRequestAccountDto;
import ru.yandex.practicum.mba.core.accounts.model.Account;
import ru.yandex.practicum.mba.core.accounts.security.UserContext;

@Mapper
public interface AccountMapper {
    AccountDto toDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    Account toEntity(UserContext userContext);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "email", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account update(@MappingTarget Account account, UpdateRequestAccountDto updateRequest);
}
