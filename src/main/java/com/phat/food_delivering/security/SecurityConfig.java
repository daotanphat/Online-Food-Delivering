package com.phat.food_delivering.security;

import com.phat.food_delivering.model.USER_ROLE;
import com.phat.food_delivering.security.filter.ExceptionHandlerFilter;
import com.phat.food_delivering.security.filter.JwtAuthorizationFilter;
import com.phat.food_delivering.security.manager.CustomAuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import com.phat.food_delivering.security.filter.AuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/login");
        http
                .headers(headers -> headers.frameOptions(frameOption -> frameOption.disable()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers("api/admin/**").hasAnyAuthority("ADMIN", "RESTAURANT_OWNER")
                        //.requestMatchers("api/**").authenticated()
                        .requestMatchers("api/user/**").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, SecurityConstants.AUTH_PATH).permitAll()
                        .anyRequest().permitAll())
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JwtAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000"
                ));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
        };
    }
}
