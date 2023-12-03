package ru.dmitrii.speakerWEBapp.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.LocaleResolver;
import ru.dmitrii.speakerWEBapp.DAO.UserDAO;
import ru.dmitrii.speakerWEBapp.models.Limitation;
import ru.dmitrii.speakerWEBapp.models.Messages.MessageJSON;
import ru.dmitrii.speakerWEBapp.models.Subscribe;
import ru.dmitrii.speakerWEBapp.models.User;
import ru.dmitrii.speakerWEBapp.security.UserDetails_Impl;
import ru.dmitrii.speakerWEBapp.util.UserValidator;

import java.util.List;
import java.util.Locale;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final UserValidator userValidator;
    private final LocaleResolver localeResolver;

    @Autowired
    public UserService(UserDAO userDAO, UserValidator userValidator, LocaleResolver localeResolver) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
        this.localeResolver = localeResolver;
    }

    public int getUserIDByUsername(String username) {
        return userDAO.getUserIDByUsername(username);
    }
    public void createUser(User user) {
        userDAO.createUser(user);
    }
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
    public void validateCreateUser(User user, String birthday,
                                   BindingResult result, HttpServletRequest http) {
        Locale locale = localeResolver.resolveLocale(http);
        userValidator.validateCreateUsername(user, result, locale);
        userValidator.birthdayValidate(user, result, birthday, locale);
        userValidator.passwordValidate(user, result, locale);
    }
    public void validateUpdateUser(User user, String birthday,
                                   BindingResult result, HttpServletRequest http) {
        Locale locale = localeResolver.resolveLocale(http);
        userValidator.validateUpdateUsername(user, result, locale);
        userValidator.birthdayValidate(user, result, birthday, locale);
        userValidator.passwordValidate(user, result, locale);
    }

    public int getUserLimValue(Authentication authentication) {
        if (authentication != null) {return ((UserDetails_Impl)authentication.getPrincipal()).getLimvalue();}
        return -1;
    }

    public String getLimName(int id) {
        return userDAO.getLimName(id);
    }

    public int getLimValue(int id) {
        return userDAO.getLimValue(id);
    }

    public String getSubscribeName(int id) {
        return userDAO.getSubscribeName(id);
    }

    public double getSubscribeCost(int id) {return userDAO.getSubscribeCost(id);}

    public List<Limitation> getLimitations(int id) {
        return userDAO.getAllLimitations(id);
    }

    public List<Subscribe> getSubscribes(int id) {
        return userDAO.getAllSubscribes(id);
    }



    // TODO: implement follow functions in UserDao
    public void updateLibrary(MessageJSON messageJSON, int id) {
        if (messageJSON.songsADD() != null) {addMusic(messageJSON.songsADD(), id);}
        if (messageJSON.songsDELETE() != null) {deleteMusic(messageJSON.songsDELETE(), id);}
        if (messageJSON.albumsADD() != null) {addAlbums(messageJSON.albumsADD(), id);}
        if (messageJSON.albumsDELETE() != null) {deleteAlbums(messageJSON.albumsDELETE(), id);}
    }

    public void addMusic(Integer[] songs, int id) {
        if (songs.length > 0) {userDAO.addMusic(songs, id);}
    }
    public void deleteMusic(Integer[] songs, int id) {
        if (songs.length > 0) {userDAO.deleteMusic(songs, id);}
    }
    public void addAlbums(Integer[] albums, int id) {
        if (albums.length > 0) {userDAO.addAlbums(albums, id);}
    }
    public void deleteAlbums(Integer[] albums, int id) {
        if (albums.length > 0) {userDAO.deleteAlbums(albums, id);}
    }
}
