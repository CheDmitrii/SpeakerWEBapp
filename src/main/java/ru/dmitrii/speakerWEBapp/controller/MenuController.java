package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.speakerWEBapp.security.UserDetails_Impl;
import ru.dmitrii.speakerWEBapp.service.MusicService;
import ru.dmitrii.speakerWEBapp.service.UserService;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final MessageSource messageSource;
    private final MusicService musicService;
    private final UserService userService;
    @Autowired
    public MenuController(MessageSource messageSource, MusicService musicService, UserService userService) {
        this.messageSource = messageSource;
        this.musicService = musicService;
        this.userService = userService;
    }
    @GetMapping("")
    public String getMainMenu() {
        return "menus/mainMenu";
    }


    @GetMapping("/search")
    public String getSearch(Model model, Authentication authentication) {

        model.addAttribute("music", musicService.getAllSongs(authentication));
        model.addAttribute("limval", userService.getUserLimValue(authentication));

        return "menus/search";
    }

    @PostMapping("/search")
    public String postFindSearch(@RequestParam(value = "music", required = false, defaultValue = "") String song,
                                 @RequestParam(value = "artist", required = false, defaultValue = "") String artist,
                                 Authentication authentication,
                                 Model model) {

        model.addAttribute("music", musicService.findSongs(authentication, song, artist));
        model.addAttribute("limval", userService.getUserLimValue(authentication));
        model.addAttribute("songVal", song);
        model.addAttribute("artistVal", artist);

        return "menus/search";
    }

    @GetMapping("/library")
    public String getLibrary(Model model, Authentication authentication) {

        model.addAttribute("usersongs", musicService.getUsersSongs(authentication));

        return "menus/library";
    }

    @PostMapping("/library")
    public String postLibrary(@RequestParam(value = "song", required = false, defaultValue = "") String song,
                              @RequestParam(value = "artist", required = false, defaultValue = "") String artist,
                              Authentication authentication,
                              Model model) {

        model.addAttribute("usersongs",
                musicService.findSongsLibrary(((UserDetails_Impl)authentication.getPrincipal()).getID(), song, artist)
        );
        model.addAttribute("songVal", song);
        model.addAttribute("artistVal", artist);

        return "menus/library";
    }


    @GetMapping("/artists")
    public String getArtists(Model model) {
        model.addAttribute("artists", musicService.getAllArtists());
        return "menus/artists";
    }

    @PostMapping("/artists")
    public String postArtists(@RequestParam(value = "artist", required = false, defaultValue = "") String artist,
                              @RequestParam(value = "album", required = false, defaultValue = "") String album,
                              Model model) {

        model.addAttribute("artists", musicService.findArtists(artist, album));
        model.addAttribute("artistVal", artist);
        model.addAttribute("albumVal", album);

        return "menus/artists";
    }

    @GetMapping("/albums")
    public String getAlbums(Model model, Authentication authentication) {

        model.addAttribute("albums", musicService.getAllAlbums(authentication));
        model.addAttribute("limval", userService.getUserLimValue(authentication));

        return "menus/albums";
    }

    @PostMapping("/albums")
    public String postAlbums(@RequestParam(value = "album", required = false, defaultValue = "") String album,
                             @RequestParam(value = "artist", required = false,defaultValue = "") String artist,
                             Authentication authentication,
                             Model model) {

        model.addAttribute("limval", userService.getUserLimValue(authentication));
        model.addAttribute("albums", musicService.findAlbums(authentication, album, artist));
        model.addAttribute("albumVal", album);
        model.addAttribute("artistVal", artist);

        return "menus/albums";
    }
}