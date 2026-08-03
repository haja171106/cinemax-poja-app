package com.school.haja.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConf {

  private final XUserIdAuthenticationFilter xUserIdAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(GET, "/ping", "/health/**")
                    .permitAll()
                    .requestMatchers(GET, "/projections")
                    .permitAll()
                    .requestMatchers(PUT, "/projections")
                    .hasRole("MANAGER")
                    .requestMatchers(PUT, "/movies")
                    .hasRole("MANAGER")
                    .requestMatchers(GET, "/reservations")
                    .hasAnyRole("EMPLOYEE", "MANAGER")
                    .requestMatchers(GET, "/reservations/*")
                    .hasAnyRole("CLIENT", "EMPLOYEE", "MANAGER")
                    .requestMatchers(PUT, "/reservations")
                    .hasAnyRole("EMPLOYEE", "MANAGER")
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(xUserIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
