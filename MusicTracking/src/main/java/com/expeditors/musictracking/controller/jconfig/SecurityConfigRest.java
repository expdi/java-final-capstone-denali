package com.expeditors.musictracking.controller.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigRest {

    @Bean
    public SecurityFilterChain courseRatingFilterChain(HttpSecurity http,
                                                       AuthExceptionHandler authExceptionHandler,
                                                       AccessExceptionHandler accessExceptionHandler) throws Exception {

        http.securityMatcher("/Artist/**", "/Tracks/**");

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.GET, "/Artist/**", "/Tracks/**").authenticated();

            auth.requestMatchers(HttpMethod.PUT).hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST).hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN");

        });

        http.httpBasic(customizer -> customizer.authenticationEntryPoint(authExceptionHandler));

        http.exceptionHandling(cust -> cust.accessDeniedHandler(accessExceptionHandler));

        http.csrf(AbstractHttpConfigurer::disable);

        var chain = http.build();
        return chain;
    }
}
