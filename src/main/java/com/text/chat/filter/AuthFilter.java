package com.text.chat.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.text.chat.exception.ApplicationException;
import com.text.chat.model.ApplicationRequest;
import com.text.chat.model.User;
import com.text.chat.service.IAuthService;
import com.text.chat.util.ExceptionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class AuthFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @Autowired
    IAuthService service;

    private static final ObjectMapper OM = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException {
        try {
            if (request.getRequestURI().contains("user/create")) {
                filterChain.doFilter(request, response);
            } else {
                CustomHttpServletRequestWrapper servletRequest = new CustomHttpServletRequestWrapper(request);
                User user = OM.readValue(servletRequest.getInputStream(), ApplicationRequest.class).getRequestedBy();
                if (user == null) {
                    throw new ApplicationException("requestedBy not specified in the req",
                            ExceptionStatus.INVALID_PAYLOAD);
                }
                LOGGER.info("Authenticating user {}", user.getId());
                //service.validateUser(user, true);
                LOGGER.info("User {} authentication successful", user.getId());
                filterChain.doFilter(servletRequest, response);
            }
        } catch (ApplicationException e) {
            LOGGER.error("Authentication failed", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            OM.writeValue(response.getWriter(), e);
        } catch (Exception e) {
            LOGGER.error("Authentication failed", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            OM.writeValue(response.getWriter(), new ApplicationException(ExceptionStatus.UNEXPECTED_ERROR));
        }
    }
}
