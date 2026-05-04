package ru.yandex.practicum.mba.core.accounts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationConverter converter) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(converter))
                )
                .build();
    }

    @Bean
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
}
