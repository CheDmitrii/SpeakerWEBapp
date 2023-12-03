package ru.dmitrii.speakerWEBapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dmitrii.speakerWEBapp.DAO.UserDAO;
import ru.dmitrii.speakerWEBapp.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;

@Component
public class UserValidator implements Validator {
    private final UserDAO userDAO;
    private final ResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public UserValidator(UserDAO userDAO, ResourceBundleMessageSource bundleMessageSource) {
        this.userDAO = userDAO;
        this.bundleMessageSource = bundleMessageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getPassword().length() < 6) {
            errors.rejectValue("password","", "bad password");
        }
    }

    public void validateCreateUsername(Object target, Errors errors, Locale locale) {
        User user = (User) target;
        if (userDAO.isUser(user.getUsername())) {
            errors.rejectValue("username", "",
                    bundleMessageSource.getMessage("error.registration.username.exist", null, locale));
        }
    }

    public void validateUpdateUsername(Object target, Errors errors, Locale locale) {
        User user = (User) target;
        if (userDAO.checkTakenUpdateUsername(user.getUsername(), user.getId())) {
            errors.rejectValue("username", "",
                    bundleMessageSource.getMessage("error.registration.username.exist", null, locale));
        }
    }

    public void birthdayValidate(Object target, Errors errors, String date, Locale locale) {
        User user = (User) target;
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        format.setLenient(false);

        try {
            user.setBirthday(format.parse(date));
        } catch (Exception e) {
            errors.rejectValue("birthday", "",
                    bundleMessageSource.getMessage("error.registration.birthday.value.wrong", null, locale));
            return;
        }

        Calendar birthday = Calendar.getInstance(), end = Calendar.getInstance();
        birthday.setTime(user.getBirthday());
        end.setTime(new Date());
        int diff = end.get(YEAR) - birthday.get(YEAR);
        if (birthday.get(MONTH) > end.get(MONTH) ||
                (birthday.get(MONTH) == end.get(MONTH) && birthday.get(DATE) > end.get(DATE))) {
            diff--;
        }

        if (diff < 14) {
            errors.rejectValue("birthday", "",
                    bundleMessageSource.getMessage("error.registration.birthday.value.invalid", null, locale));
        }
//        if (Period.between(LocalDate.ofInstant(user.getBirthday().toInstant(), ZoneId.systemDefault()), LocalDate.now()).getYears() < 14){
//            errors.rejectValue("birthday", "", "Age should be greader or equals 14 years");
//        }
    }

    public void passwordValidate(Object target, Errors errors,  Locale locale) {
        User user = (User) target;
        if (user.getPassword().length() < 6) {
            errors.rejectValue("password","",
                    bundleMessageSource.getMessage("error.registration.password.length", null, locale));
        }
    }
}
