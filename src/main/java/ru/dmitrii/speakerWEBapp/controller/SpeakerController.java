package ru.dmitrii.speakerWEBapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Dates;
import ru.dmitrii.speakerWEBapp.DAO.UserDAO;
import ru.dmitrii.speakerWEBapp.models.User;
import ru.dmitrii.speakerWEBapp.util.UserValidator;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


// TODO: implement handler format of data in service

@Controller
@RequestMapping("/speaker")
public class SpeakerController {
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    @Autowired
    public SpeakerController(UserDAO userDAO, UserValidator userValidator) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }


    @GetMapping("/wellcome")
    public String welcomePage() {
        return "logins/wellcome_page";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginError", false);
//        model.addAttribute("user", new User());
//        model.addAttribute("login", null);
//        model.addAttribute("password", null);
        return "logins/loginPage";
    }


    @PostMapping("/login")
    public String login(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password,
                        Model model){

        // execute userValidator.passwordValidate
        // check for existing user
        if (login.equals("") || password.equals("")) {
            model.addAttribute("loginError", true);
            return "logins/loginPage";
        }
//        User user = new User();
//        user.setUserName(login);
//        model.addAttribute("user", user);
        return "redirect:/menu";
    }

    @DeleteMapping("/login")
    public String logout(Model model) {
//        model.addAttribute("user", null);
        return "redirect:/speaker/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "logins/registrationPage";
    }


    @ExceptionHandler(NullPointerException.class)
    public void hend() {
    }


    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult result) {
//        List<FieldError> list = result.getFieldErrors().stream()
//                        .filter(fieldError -> !fieldError.getField().equals("birthday")).toList();
//        result = new BeanPropertyBindingResult(user, "user");
//        for (FieldError fieldError:list) {
//            result.addError(fieldError);
//        }

//        System.out.println(result.getTarget() + "\n" + result.getObjectName());
//        userValidator.validate(user, result);
//
//        if (result.hasFieldErrors("birthday")) {
//        }
        userValidator.birthdayValidate(user, result);

        if (result.hasErrors()) {
            for (ObjectError error:result.getAllErrors()) {
//                System.out.println(error.getObjectName());
                System.out.println(error);
//                System.out.println(user.getBirthday());
                System.out.println("---------------------");
            }
            return "logins/registrationPage";
        }
//        user.setDate(Date.valueOf(LocalDate.));
//        System.out.println(user.getDatedate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")));
//        Dates dates = new Dates(Locale.ENGLISH);
//        System.out.println(dates.format(user.getBirthday(), "dd-MM-yyyy"));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
//        user.setDate(Date.valueOf(simpleDateFormat.format(user.getDate())));
//        System.out.println(user.getBirthday());
        return "redirect:/speaker/login";
    }


//    @ModelAttribute
//    public void getUser(Model model) {
//        model.addAttribute("user", new User());
//    }
}
