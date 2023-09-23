package ru.vsu.cs.lysenko.kinder.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vsu.cs.lysenko.kinder.dto.CredentialsDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UsernamePasswordAuthenticationFilter extends OncePerRequestFilter {

    public static final String SIGN_IN_ENDPOINT = "/sign-in";
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (SIGN_IN_ENDPOINT.equals(request.getServletPath())
                && HttpMethod.POST.matches(request.getMethod())) {
            CredentialsDTO credentialsDTO = MAPPER.readValue(request.getInputStream(), CredentialsDTO.class);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(credentialsDTO.getUsername(), credentialsDTO.getPassword())
            );
        }
        filterChain.doFilter(request, response);
    }
}
