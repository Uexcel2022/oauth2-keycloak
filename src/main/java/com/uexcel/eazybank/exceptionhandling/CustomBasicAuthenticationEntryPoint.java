package com.uexcel.eazybank.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setHeader("eazybank-error-reason", "Authentication Failed");

        String msg = (authException!=null && authException.getMessage()!=null)?
                authException.getMessage():" Unauthorized";

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String jsonResponse =
                String.format("{\"timestamp\":\"%s\",\"status\":\"%d\",\"error\":\"%s\"," +
                                "\"message\":\"%s\",\"apiPath\":\"%s\"}", getTime(),HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(), msg,request.getRequestURI());

        response.setContentType("application/json,charset=utf-8");
        response.getWriter().write(jsonResponse);
    }

    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
