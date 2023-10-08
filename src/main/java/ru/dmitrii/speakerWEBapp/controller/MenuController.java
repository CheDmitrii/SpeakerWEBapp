package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @GetMapping("/search")
    public String getSearch() {
        return "menus/search";
    }

    @GetMapping("/library")
    public String getLibrary() {
        return "menus/library";
    }

    @GetMapping("")
    public String getMainMenu() {
        return "menus/mainMenu";
    }

    @PostMapping("")
    public String choseMenu() {
        return null;
    }


    @ModelAttribute
    public void user(Model model) {
        model.addAttribute("user", "FGHJKJHGFGHJK");
    }

//    @GetMapping("/search")
//    public String getSong(@RequestParam(value = "singer", required = false) String singer,
//                          @RequestParam(value = "song", required = false) String song,
//                          Model model) {
//        model.addAttribute("searchError", true);
//        if (singer != null || song != null) {
//        } else {
//            model.addAttribute("searchError", true);
//        }
//        return "menus/search";
//    }

//    @GetMapping("/showSong")
//    public String show() {
//        return "players/showSong";
//    }

}
