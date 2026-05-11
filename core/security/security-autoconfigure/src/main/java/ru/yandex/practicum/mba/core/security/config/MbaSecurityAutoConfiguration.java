package ru.yandex.practicum.mba.core.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import ru.yandex.practicum.mba.core.security.user.UserContextMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@AutoConfiguration
@ConditionalOnClass({HttpSecurity.class, JwtAuthenticationConverter.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MbaSecurityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {

            Collection<GrantedAuthority> authorities = new ArrayList<>();

            // 1. realm_access.roles
            if (jwt.hasClaim("realm_access")) {
                List<String> roles = (List<String>) jwt.getClaimAsMap("realm_access").get("roles");

                if (roles != null) {
                    authorities.addAll(
                            roles.stream()
                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                    .toList()
                    );
                }
            }

            // 2. resource_access (Keycloak client roles)
            if (jwt.hasClaim("resource_access")) {
                jwt.getClaimAsMap("resource_access").forEach((client, value) -> {
                    Map<String, Object> clientData = (Map<String, Object>) value;
                    List<String> roles = (List<String>) clientData.get("roles");

                    if (roles != null) {
                        authorities.addAll(
                                roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .toList()
                        );
                    }
                });
            }

            return authorities;
        });

        return converter;
    }

    @Bean
    @ConditionalOnMissingBean(UserContextMapper.class)
    public UserContextMapper userContextMapper() {
        return new UserContextMapper();
    }
}