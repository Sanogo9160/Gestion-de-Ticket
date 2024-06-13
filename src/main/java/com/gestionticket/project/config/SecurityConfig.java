package com.gestionticket.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        //.requestMatchers("/api/tickets/**").hasAnyRole("APPRENANT", "FORMATEUR", "ADMIN")
                        .requestMatchers("/api/users/register").hasRole("ADMIN") // Seul l'ADMIN peut accéder à cette URL
                        .requestMatchers("/api/**").authenticated() // Authentification requise pour tous les autres endpoints
                        .anyRequest().authenticated()
                )
                .httpBasic( Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails apprenant = User.withDefaultPasswordEncoder()
                .username("apprenant")
                .password("password")
                .roles("APPRENANT")
                .build();

        UserDetails formateur = User.withDefaultPasswordEncoder()
                .username("formateur")
                .password("password")
                .roles("FORMATEUR")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(apprenant, formateur, admin);
    }
}
