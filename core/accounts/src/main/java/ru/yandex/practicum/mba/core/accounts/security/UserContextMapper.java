package ru.yandex.practicum.mba.core.accounts.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UserContextMapper {
    public UserContext from(Jwt jwt) {
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");

        String name = Stream.of(lastName, firstName)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));

        return new UserContext(
                jwt.getSubject(),
                jwt.getClaimAsString("preferred_username"),
                name,
                jwt.getClaimAsString("email")
        );
    }
}