package ru.dmitrii.speakerWEBapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmitrii.speakerWEBapp.DAO.UserDAO;
import ru.dmitrii.speakerWEBapp.models.User;
import ru.dmitrii.speakerWEBapp.security.UserDetails_Impl;

import java.util.Optional;

@Service
public class UserDetailsService_Impl implements UserDetailsService {
    private final UserDAO userDAO;
    @Autowired
    public UserDetailsService_Impl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = null;
        try {
             user = Optional.ofNullable(userDAO.getUser(username));
        } catch (DataAccessException e) {
            throw  new UsernameNotFoundException("User doesn't exist");
        }

        if (user.isEmpty()) {throw new UsernameNotFoundException("User doesn't exist");}

        return new UserDetails_Impl(user.get());
    }
}
