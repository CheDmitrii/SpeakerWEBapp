package ru.dmitrii.speakerWEBapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmitrii.speakerWEBapp.DAO.MusicDAO;
import ru.dmitrii.speakerWEBapp.models.User;

//@Controller
//@RequestMapping("/menu/search")
public class SearchController {
    private User user;
    private MusicDAO musicDAO;

//    @Autowired
    public SearchController(User user, MusicDAO musicDAO) {
        this.musicDAO = musicDAO;
        this.user = user;
    }

//    @GetMapping("")
    public String getSearch(Model model) {
        model.addAttribute("music", musicDAO.getAllSong());
        model.addAttribute("testText", "Helllo \n this is tests");
        return "menus/search"; // eeeeeeeeee
    }

    @ModelAttribute
    public void setUser(Model model) {
        model.addAttribute("user", "TestUser");
    }
}
