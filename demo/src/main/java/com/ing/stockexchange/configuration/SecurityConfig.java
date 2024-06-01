package com.ing.stockexchange.configuration;

import com.ing.stockexchange.repository.UserRepository;
import com.ing.stockexchange.service.JpaUserDetailsService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.bcel.BcelCflowAccessVar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig  {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                        .requestMatchers("/api/v1/**").authenticated() // Restrict API endpoints to authenticated users
                        .anyRequest().authenticated() // Restrict other requests
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/v1/**") // Disable CSRF protection for H2 console and API endpoints
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Disable frame options for H2 console
                )
                .httpBasic(Customizer.withDefaults()); // Enable Basic Auth with defaults

        return http.build();
    }
}