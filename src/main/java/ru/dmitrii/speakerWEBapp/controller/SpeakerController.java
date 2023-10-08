package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.speakerWEBapp.DAO.UserDAO;
import ru.dmitrii.speakerWEBapp.models.User;

@Controller
@RequestMapping("/speaker")
public class SpeakerController {
    private final UserDAO userDAO;

    @Autowired
    public SpeakerController(UserDAO userDAO) {
        this.userDAO = userDAO;
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

        // check for existing user
        if (login.equals("") || password.equals("")) {
            model.addAttribute("loginError", true);
            return "logins/loginPage";
        }
        User user = new User();
        user.setUserName(login);
        model.addAttribute("user", user);
        return "redirect:/menu";
    }

    @DeleteMapping("/login")
    public String logout(Model model) {
        model.addAttribute("user", null);
        return "redirect:/speaker/login";
    }

    @ModelAttribute
    public void getUser(Model model) {
        model.addAttribute("user", new User());
    }
}
