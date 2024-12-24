package com.uexcel.eazybank.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Profile("!prod")
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {
    private final EazyBankUserDetailsService userDetailsService;
    /**
     * @param authentication the authentication request object.
     * @throws AuthenticationException - exception
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }
    /**
     * @param authentication - authentication
     * @return - authentication type
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
