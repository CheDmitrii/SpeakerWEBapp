package ru.dmitrii.speakerWEBapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.expression.Dates;
import ru.dmitrii.speakerWEBapp.DAO.UserDAO;
import ru.dmitrii.speakerWEBapp.models.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Component
public class UserValidator implements Validator {
    private final UserDAO userDAO;

    @Autowired
    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userDAO.isUser(user.getUserName())) {
            errors.rejectValue("userName", "", "Username is already taken");
        }
    }

    public void birthdayValidate(Object target, Errors errors) {
        User user = (User) target;
//        Dates dates = new Dates(Locale.ENGLISH);
        if (user.getBirthday() == null) {
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthday", "", "");
            errors.rejectValue("birthday", "", "Wrong birthday");
            return;
        }
//        if (Period.between(LocalDate.ofInstant(user.getBirthday().toInstant(), ZoneId.systemDefault()), LocalDate.now()).getYears() < 14){
//            errors.rejectValue("birthday", "", "Age should be greader or equals 14 years");
//        }
    }

//    public void checkDateString(String date, Errors errors) {
//
//        try {
//            Date date1 = new Date();
//            java.sql.Date.valueOf(date);
//        } catch (Exception exception){
//
//        }
//    }
    public void passwordValidate(Object target, Errors errors) {
        User user = (User) target;
        if (!userDAO.rightPassword(user.getUserName(), user.getPassword())) {
            errors.rejectValue("password","", "Wrong password");
        }
    }
}
