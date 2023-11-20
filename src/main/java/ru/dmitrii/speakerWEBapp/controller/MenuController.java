package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dmitrii.speakerWEBapp.DAO.MusicDAO;

import java.util.Locale;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final MusicDAO musicDAO;
    private final MessageSource messageSource;
    @Autowired
    public MenuController(MusicDAO musicDAO, MessageSource messageSource) {
        this.musicDAO = musicDAO;
        this.messageSource = messageSource;
    }
    @GetMapping("")
    public String getMainMenu() {return "menus/mainMenu";}

//    @PostMapping("")
//    public String choseMenu(@RequestParam(value = "kind", required = false) String kind) {
//        return switch (kind) {
//            case "search" -> "redirect:menu/search";
//            case "library" -> "redirect:menu/library";
//            default -> "menus/mainMenu";
//        };
//    }

    @GetMapping("/albums")
    public String getAlbums(Model model) {
        model.addAttribute("albums", musicDAO.getAllAlbums());
        return "menus/albums";
    }

    @GetMapping("/search")
    public String getSearch(Model model) {
        model.addAttribute("music", musicDAO.getAllSong());
        return "menus/search";
    }

    @GetMapping("/artists")
    public String getArtists(Model model) {
        model.addAttribute("artists", musicDAO.getAllArtist());
        return "menus/artists";
    }

    @GetMapping("/library")
    public String getLibrary() {
//        Locale locale = Locale.US; // @RequestParam(value = "lang", required = false) String lang
//        if (lang != null && !lang.isEmpty()) {
//            locale = new Locale(lang);
//        }
//        System.out.println(messageSource.getMessage("test", null, "failed", locale));
//        System.out.println(messageSource.getMessage("test", null, "def mess", new Locale("ru")));
//        System.out.println("----------------------------------");
        return "menus/library";
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
