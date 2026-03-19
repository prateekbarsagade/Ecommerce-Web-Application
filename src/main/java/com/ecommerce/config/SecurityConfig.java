package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ecommerce.security.JwtFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.ecommerce")
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    //Password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Main Security Configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF
            .csrf(csrf -> csrf.disable())

            // Stateless session (JWT)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Authorization Rules
            .authorizeHttpRequests(auth -> auth

            	    // PUBLIC APIs
            	    .requestMatchers(new AntPathRequestMatcher("/api/auth/**")).permitAll()
            	    .requestMatchers(new AntPathRequestMatcher("/api/products/**")).permitAll()
            	    .requestMatchers(new AntPathRequestMatcher("/api/categories/**")).permitAll()
            	    .requestMatchers(new AntPathRequestMatcher("/api/users")).permitAll()

            	    // ADMIN APIs
            	    .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")

            	    // USER APIs
            	    .requestMatchers(new AntPathRequestMatcher("/api/cart/**")).authenticated()
            	    .requestMatchers(new AntPathRequestMatcher("/api/orders/**")).authenticated()

            	    // ALL OTHER APIs
            	    .anyRequest().authenticated()
            	)
            
            // Add JWT Filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ✅ Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}