package ru.vsu.cs.lysenko.kinder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.vsu.cs.lysenko.kinder.dto.CredentialsDTO;
import ru.vsu.cs.lysenko.kinder.dto.UserDTO;
import ru.vsu.cs.lysenko.kinder.services.AuthenticationService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDTO userDTO = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            userDTO = authenticationService.authenticateByCredentials(
                    new CredentialsDTO(authentication.getPrincipal().toString(), authentication.getCredentials().toString()));
        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            userDTO = authenticationService.findBySession(authentication.getPrincipal().toString());
        }
        if (Objects.isNull(userDTO)) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDTO, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
