package com.uexcel.eazybank.config;

import com.uexcel.eazybank.filter.CsrfCookieFilter;
import com.uexcel.eazybank.exceptionhandling.CustomAccessDeniedHandler;
import com.uexcel.eazybank.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.uexcel.eazybank.filter.CustomAuthicationValidationFilter;
import com.uexcel.eazybank.filter.CustomRequestValidationFilter;
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
@Profile("!prod")
public class ProjectSecurityConfigNotProd {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new CustomRequestValidationFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new CustomAuthicationValidationFilter(), BasicAuthenticationFilter.class);


        http.requiresChannel(rcc-> rcc.anyRequest().requiresInsecure());
        http.sessionManagement(smc->smc
                .invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true));

        http.csrf(csrfConfig->csrfConfig
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.sessionManagement(sc->sc.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/invalidSession","/api/register").permitAll()
                .requestMatchers("/api/myAccounts","/api/myBalance").hasRole("USER")
                .requestMatchers("/api/myLoans","/api/myCards").hasRole("USER")
                .requestMatchers("/api/contact","/api/notices","/error").permitAll()
                .anyRequest().authenticated());
        http.formLogin(withDefaults());
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc->ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        //when it's required
//        http.exceptionHandling(ehc->ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())); //global config
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
