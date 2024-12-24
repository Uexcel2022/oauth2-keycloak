package com.uexcel.eazybank.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationEvents {
    @EventListener
    public void listen(AuthorizationDeniedEvent event) {
        log.error("Authorization fail for user: {} due to {}",
                event.getAuthentication().get().getName(),
                event.getAuthorizationDecision().toString());
    }
}
