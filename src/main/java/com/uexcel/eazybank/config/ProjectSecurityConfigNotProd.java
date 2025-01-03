package com.uexcel.eazybank.config;

import com.uexcel.eazybank.converter.KeyCloakRoleConverter;
import com.uexcel.eazybank.exceptionhandling.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfigNotProd {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());

        http.requiresChannel(rcc-> rcc.anyRequest().requiresInsecure());

        http.csrf(csrfConfig->csrfConfig
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/api/register").permitAll()
                .requestMatchers("/api/fetch-customer","/api/create-account").hasRole("USER")
                .requestMatchers("/api/myAccounts","/api/myBalance",
                        "/api/updateLoan","/api/add-transaction").hasRole("USER")
                .requestMatchers("/api/myLoans","/api/myCards","/api/addLoan").hasRole("USER")
                .requestMatchers("/api/contact","/api/notices","/error").permitAll()
                .requestMatchers("/api/add-notice","/api/update-notice").hasRole("ADMIN")
                .anyRequest().authenticated());
        http.oauth2ResourceServer(rsc->rsc.jwt(jwtConfigurer -> jwtConfigurer
                .jwtAuthenticationConverter(jwtAuthenticationConverter)));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }




}
