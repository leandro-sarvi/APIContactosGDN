package com.agenda.Contactos.Tienda.config.security;

import com.agenda.Contactos.Tienda.services.IJwtUtilityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private IJwtUtilityService jwtUtilityService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfig(IJwtUtilityService jwtUtilityService, JwtAuthenticationFilter jwtAuthenticationFilter){
        this.jwtUtilityService = jwtUtilityService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sesionManagement ->
                        sesionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                            // Endpoint Publicos

                            auth.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                            auth.requestMatchers(HttpMethod.POST,"/auth/register").permitAll();
                            auth.requestMatchers(HttpMethod.GET,"/auth/confirm-mail/**").permitAll();

                            // Endpoint Privados

                            auth.requestMatchers(HttpMethod.POST,"/auth/userdelete/**").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.GET,"/auth/admin-access").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.GET,"/auth/sessionverifier").hasAuthority("ADMIN");

                            auth.requestMatchers(HttpMethod.POST, "/api/v1/contactoscreate").hasAuthority("ADMIN");
                            auth.requestMatchers(HttpMethod.DELETE, "/api/v1/contactos/deletebyid/**").hasAuthority("ADMIN");
                            auth.anyRequest().authenticated();
                        }
                        )

                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint((request,response,authRequest)->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED")
                                        )
                        )
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
