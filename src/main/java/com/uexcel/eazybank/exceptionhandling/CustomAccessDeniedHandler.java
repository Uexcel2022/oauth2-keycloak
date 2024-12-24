package com.uexcel.eazybank.exceptionhandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException
            accessDeniedException) throws IOException, ServletException {
        response.setHeader("eazybank-denied-reason", "Authentication Failed");

        String msg = (accessDeniedException!=null && accessDeniedException.getMessage()!=null)?
                accessDeniedException.getMessage():"Authentication Failed";

        response.setStatus(HttpStatus.FORBIDDEN.value());

        String jsonResponse =
                String.format("{\"timestamp\":\"%s\",\"status\":\"%d\",\"error\":\"%s\"," +
                                "\"message\":\"%s\",\"apiPath\":\"%s\"}", getTime(),HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(), msg,request.getRequestURI());

        response.setContentType("application/json,charset=utf-8");
        response.getWriter().write(jsonResponse);
    }
    private String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
