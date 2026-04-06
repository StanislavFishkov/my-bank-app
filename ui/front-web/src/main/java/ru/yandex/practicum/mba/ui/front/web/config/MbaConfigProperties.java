package ru.yandex.practicum.mba.ui.front.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mba")
public record MbaConfigProperties(String apiUrl, boolean clientWiretapEnabled) {
}