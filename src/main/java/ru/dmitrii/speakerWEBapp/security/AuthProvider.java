package ru.dmitrii.speakerWEBapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import ru.dmitrii.speakerWEBapp.service.UserDetailsService_Impl;

import java.util.Collections;


//@Component // don't sure
public class AuthProvider implements AuthenticationProvider {

    private final UserDetailsService_Impl userDetailsService;

//    @Autowired
    public AuthProvider(UserDetailsService_Impl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();


        System.out.println("Password from authenticate " + password);

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        return new PreAuthenticatedAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
