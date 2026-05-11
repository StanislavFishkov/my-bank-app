package ru.yandex.practicum.mba.core.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.yandex.practicum.mba.core.security.user.UserContextArgumentResolver;
import ru.yandex.practicum.mba.core.security.user.UserContextMapper;

import java.util.List;

@AutoConfiguration
@ConditionalOnClass(WebMvcConfigurer.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MbaUserContextAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserContextArgumentResolver.class)
    public UserContextArgumentResolver userContextArgumentResolver(UserContextMapper mapper) {
        return new UserContextArgumentResolver(mapper);
    }

    @Bean
    public WebMvcConfigurer mbaUserContextWebMvcConfigurer(UserContextArgumentResolver resolver) {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(resolver);
            }
        };
    }
}