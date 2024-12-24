package com.uexcel.eazybank.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Slf4j
public class CustomRequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String timedHeader = header.trim();
            if (StringUtils.startsWithIgnoreCase(timedHeader, "Basic ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            byte[] base64Token = timedHeader.substring(6).getBytes(StandardCharsets.UTF_8);
            byte[] decoded;
            try {
                decoded = Base64.getDecoder().decode(base64Token);
                String token = (new String(decoded, StandardCharsets.UTF_8)).trim();
                int delim = token.indexOf(':');
                if (delim == -1) {
                    throw new BadCredentialsException("Invalid basic authentication token");
                }
                String username = token.substring(0, delim);
                String password = token.substring(delim + 1);
                // for demo purpose
                if(username.contains("xxx") || password.length() < 8) {
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().println("{\"response\": \"Bad credentials\"}");
                    return;
                }

            } catch (Exception e) {
                throw new BadCredentialsException("Fail to decode basic authentication base64Token", e);
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);

    }
}
