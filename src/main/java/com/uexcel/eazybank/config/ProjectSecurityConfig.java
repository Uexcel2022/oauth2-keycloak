package com.uexcel.eazybank.config;

import com.uexcel.eazybank.filter.CsrfCookieFilter;
import com.uexcel.eazybank.exceptionhandling.CustomAccessDeniedHandler;
import com.uexcel.eazybank.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.requiresChannel(rcc-> rcc.anyRequest().requiresSecure());
        http.sessionManagement(smc->smc
                .invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true));

        http.csrf(csrfConfig->csrfConfig
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.sessionManagement(sc->sc.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/myAccounts","/api/myBalance").hasRole("USER")
                .requestMatchers("/api/myLoans","/api/myCards").hasRole("USER")
                .requestMatchers("/api/contact","/api/notices","/error").permitAll()
                .requestMatchers("/invalidSession","/api/register").permitAll()
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }



}
