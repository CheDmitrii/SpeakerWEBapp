package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmitrii.speakerWEBapp.DAO.MusicDAO;
import ru.dmitrii.speakerWEBapp.service.MusicService;
import ru.dmitrii.speakerWEBapp.service.UserService;

@Controller
@RequestMapping("/show")
public class ShowController {

    private MusicDAO musicDAO;
    private final MusicService musicService;
    private final UserService userService;

    @Autowired
    public ShowController(MusicDAO musicDAO, MusicService musicService, UserService userService) {
        this.musicDAO = musicDAO;
        this.musicService = musicService;
        this.userService = userService;
    }

    @GetMapping("/song/{id}")
    public String getSong(@PathVariable("id") int id, Model model,
                          Authentication authentication) {
        model.addAttribute("song", musicService.getSongByID(id, authentication));
        return "show/song";
    }

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") int id, Model model,
                            Authentication authentication) {

        model.addAttribute("artist", musicService.getArtistByID(id));
        model.addAttribute("albums", musicService.getAlbumsOfArtistByID(id, authentication));
        return "show/artist";
    }

    @GetMapping("/album/{id}")
    public String getAlbum(@PathVariable("id") int id, Model model, Authentication authentication) {
        model.addAttribute("album", musicService.getAlbum(id, authentication));
        return "show/album";
    }

    @ModelAttribute
    public void addLimVal(Authentication authentication, Model model) {
        model.addAttribute("limval", userService.getUserLimValue(authentication));
    }
}
