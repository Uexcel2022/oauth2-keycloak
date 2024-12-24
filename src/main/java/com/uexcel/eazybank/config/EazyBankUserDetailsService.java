package com.uexcel.eazybank.config;

import com.uexcel.eazybank.model.Customer;
import com.uexcel.eazybank.persistence.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class EazyBankUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    /**
         * @param username the username identifying the user whose data is required.
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          Customer customer = customerRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            String.format("User details not found for the user: %s",username))
                    );

          List<GrantedAuthority> authorities =
                  customer.getAuthority().stream().map(authority -> new SimpleGrantedAuthority(authority.getName()))
                                  .collect(Collectors.toUnmodifiableList());
            return new User(customer.getEmail(),customer.getPwd(), authorities);
        }
}
