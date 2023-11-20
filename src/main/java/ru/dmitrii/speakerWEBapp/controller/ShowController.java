package ru.dmitrii.speakerWEBapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmitrii.speakerWEBapp.DAO.MusicDAO;

@Controller
@RequestMapping("/show")
public class ShowController {

    private MusicDAO musicDAO;

    @Autowired
    public ShowController(MusicDAO musicDAO) {
        this.musicDAO = musicDAO;
    }

    @GetMapping("/song/{id}")
    public String getSong(@PathVariable("id") int id, Model model) {
        model.addAttribute("song", musicDAO.getSong(id));
        return "show/song";
    }

    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable("id") int id, Model model) {
        model.addAttribute("albums", musicDAO.getAlbumsOfArtist(id));
        model.addAttribute("artist", musicDAO.getArtist(id));
        return "show/artist";
    }

    @GetMapping("/album/{id}")
    public String getAlbum(@PathVariable("id") int id, Model model) {
        model.addAttribute("album", musicDAO.getAlbum(id));
        return "show/album";
    }
}
