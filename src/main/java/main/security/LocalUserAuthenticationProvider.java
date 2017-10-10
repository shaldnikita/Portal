package main.security;

import main.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class LocalUserAuthenticationProvider implements AuthenticationProvider, Serializable {
    private static final Logger log = LoggerFactory.getLogger(LocalUserAuthenticationProvider.class);

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Override
    public Authentication authenticate(Authentication req) throws AuthenticationException {
        String login = req.getName();
        String password = req.getCredentials().toString();

        User user = userAuthenticationService.checkLogin(login, password);

        if (user != null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password, user.getAuthorities());

            return token;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
