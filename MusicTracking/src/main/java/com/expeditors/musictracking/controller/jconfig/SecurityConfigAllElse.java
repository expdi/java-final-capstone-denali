package com.expeditors.musictracking.controller.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfigAllElse {

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails bobby = User.withUsername("Raul")
                .password("{bcrypt}$2y$10$eH/LSRXeEZAvmtd.NWH6l.rT8FM16TKK7dcoD4WWB2BKWl3Ah4VMK")
                .roles("USER", "ADMIN")
                .build();

        UserDetails manoj = User.withUsername("Manor")
                .password("{bcrypt}$2y$10$BwpUmFYT7i7ccghW5IST0eMSHXxwY8xmn8kEP.lD335kbzA5M2F6q")
                .roles("USER")
                .build();

        var userDetailsService = new InMemoryUserDetailsManager(bobby, manoj);

        return userDetailsService;
    }

}
