package ru.vsu.cs.lysenko.kinder.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CookieAuthenticationFilter extends OncePerRequestFilter {
    public static final String COOKIE_NAME = "session";

    // TODO refactor this shitty code
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<Cookie> cookieAuth = Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]))
                .filter(cookie -> COOKIE_NAME.equals(cookie.getName()))
                .findFirst();
        cookieAuth.ifPresent(cookie -> SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(cookie.getValue(), null)
        ));
        filterChain.doFilter(request, response);
    }
}
