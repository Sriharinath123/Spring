package com.microservices.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .oauth2Client()
                    .and()
                .oauth2Login()
                .tokenEndpoint()
                    .and()
                .userInfoEndpoint();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        .and()
        .csrf().disable(); // Disable CSRF protection globally

        http
                .authorizeHttpRequests()
                .requestMatchers("/unauthenticated", "/api/order", "/api/inventory").permitAll() // Permit access to these endpoints without authentication                            .anyRequest()
                .anyRequest().fullyAuthenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("http://localhost:8080/realms/external/protocol/openid-connect/logout?redirect_uri=http://localhost:8180/");

        return http.build();
        
    }
}
