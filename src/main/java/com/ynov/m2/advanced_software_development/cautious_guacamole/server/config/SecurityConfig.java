package com.ynov.m2.advanced_software_development.cautious_guacamole.server.config;

import com.ynov.m2.advanced_software_development.cautious_guacamole.server.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                // Désactivation de la protection CSRF
                .csrf(csrf -> csrf.disable())

                // Configuration des règles d'autorisation
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/register").permitAll()
                        .anyRequest().authenticated()
                )

                // Ajout du filtre JWT avant le UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Retourne l'objet HttpSecurity construit
        return http.build();
    }

    //@Bean
    //public AuthenticationManager authenticationManagerBean() throws Exception {
    //    return new AuthenticationManager() {
    //        @Override
    //        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    //            return null;
    //        }
    //    };
    //}
}