package ru.dmitrii.speakerWEBapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.speakerWEBapp.models.Messages.MessageJSON;
import ru.dmitrii.speakerWEBapp.models.User;
import ru.dmitrii.speakerWEBapp.security.UserDetails_Impl;
import ru.dmitrii.speakerWEBapp.service.UserService;


@Controller
@RequestMapping("/speaker")
public class SpeakerController {
    private final UserService userService;
    @Autowired
    public SpeakerController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/wellcome")
    public String welcomePage() {
        return "logins/welcome_page";
    }

    @GetMapping("/login")
    public String login() {return "logins/login_page";}

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "logins/registration_page";
    }



//    @ExceptionHandler(UsernameNotFoundException.class)
//    public void hendl() {
//
//    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult result,
                             @RequestParam("date") String date,
                             HttpServletRequest request) {

        userService.validateCreateUser(user, date, result, request);
        if (result.hasErrors()) {return "logins/registration_page";}
        userService.createUser(user);

        return "redirect:/speaker/login";
    }

    @GetMapping("/settings")
    public String getSettings(Model model, Authentication authentication) {

        UserDetails_Impl userDetails = (UserDetails_Impl)authentication.getPrincipal();
        User user = userDetails.getUser();
        User newUser = new User(user.getId(), user.getUsername(), user.getFirstName(), user.getSecondName(),
                user.getPassword(), user.getBirthday(), user.getLimName(), user.getLimitValue(), user.getIdLim(),
                user.getIdSubscribe(), user.getSubscribeCost(), user.getSubscribeName());

        model.addAttribute("newUser", newUser);
        model.addAttribute("user", user);
        model.addAttribute("limitations", userService.getLimitations(user.getIdLim()));
        model.addAttribute("subscribes", userService.getSubscribes(user.getIdSubscribe()));

        return "logins/settings_page";
    }


    @PostMapping("/settings")
    public String updateSetting(@ModelAttribute("newUser") User updateUser,
                                BindingResult result,
                                @RequestParam("date") String date,
                                HttpServletRequest request,
                                Authentication authentication,
                                Model model) {

        User user = ((UserDetails_Impl)authentication.getPrincipal()).getUser();
        model.addAttribute("user", user);
        updateUser.setId(user.getId());

        userService.validateUpdateUser(updateUser, date, result, request);
        if (result.hasErrors()) {
            updateUser.updateUser(user);
            return "logins/settings_page";
        }

        userService.updateUser(updateUser);
        user.updateUser(updateUser);
        user.setLimName(userService.getLimName(user.getIdLim()));
        user.setLimitValue(userService.getLimValue(user.getIdLim()));
        user.setSubscribeName(userService.getSubscribeName(user.getIdSubscribe()));
        user.setSubscribeCost(userService.getSubscribeCost(user.getIdSubscribe()));

        return "redirect:/speaker/settings";
    }



    // Use to add or delete music from library when user manage his playlist
    @PostMapping(value = "/update/music", consumes = {"*/*"})
    public String updateMusic(@RequestBody MessageJSON messageJSON, Authentication authentication) {
        userService.updateLibrary(messageJSON, ((UserDetails_Impl) authentication.getPrincipal()).getID());
        return "redirect:";
    }

//    @PostMapping(value = "/update/logout/music", consumes = {"*/*"})
//    public String logoutUpdate(@RequestBody MessageJSON messageJSON) {
//        int id = userService.getUserIDByUsername(messageJSON.username());
//        userService.updateLibrary(messageJSON, id);
//        return "redirect:";
//    }
}