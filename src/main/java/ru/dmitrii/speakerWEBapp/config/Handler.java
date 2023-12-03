package ru.dmitrii.speakerWEBapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

//public class Handler extends SimpleUrlAuthenticationFailureHandler {
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        super.onAuthenticationFailure(request, response, exception);
//        if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
//            System.out.println("usernameException");
//            response.sendRedirect("?error=name");
//        }
//        if (exception.getClass().isAssignableFrom(AuthenticationException.class)) {
//            response.sendRedirect("?error=pass");
//            System.out.println("passwordException");
//        }
//    }
//}
