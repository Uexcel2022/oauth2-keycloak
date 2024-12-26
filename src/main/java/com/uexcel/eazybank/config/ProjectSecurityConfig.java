package com.uexcel.eazybank.config;

import com.uexcel.eazybank.converter.KeyCloakConverter;
import com.uexcel.eazybank.filter.CsrfCookieFilter;
import com.uexcel.eazybank.exceptionhandling.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakConverter());

        http.requiresChannel(rcc-> rcc.anyRequest().requiresSecure())
                .csrf(csrfConfig->csrfConfig
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
                .authorizeHttpRequests(requests -> requests
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
