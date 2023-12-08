package ru.dmitrii.speakerWEBapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dmitrii.speakerWEBapp.DAO.MusicDAO;
import ru.dmitrii.speakerWEBapp.models.Album;
import ru.dmitrii.speakerWEBapp.models.Artist;
import ru.dmitrii.speakerWEBapp.models.Song;
import ru.dmitrii.speakerWEBapp.security.UserDetails_Impl;

import java.util.List;

@Service
public class MusicService {

    private final MusicDAO musicDAO;

    @Autowired
    public MusicService(MusicDAO musicDAO) {
        this.musicDAO = musicDAO;
    }


    public String getPathSong(int id) {
        return musicDAO.getPathSong(id);
    }

    public String getAlbumPicture(int id) {
        return musicDAO.getAlbumPicturePath(id);
    }

    public List<Song> getAllSongs(Authentication authentication) {
        if (authentication == null) {
            return musicDAO.getAllSongsNoAuth();
        }
        return musicDAO.getAllSongsAuth(((UserDetails_Impl) authentication.getPrincipal()).getID());
    }

    // return list of songs that user add in library
    public List<Song> getUsersSongs(Authentication authentication) {
        return musicDAO.getUserSongs(((UserDetails_Impl) authentication.getPrincipal()).getID());
    }

    public List<Album> getAllAlbums(Authentication authentication) {
        if (authentication == null) {return musicDAO.getAllAlbumsNoAuth();}
        return musicDAO.getAllAlbumsAuth(((UserDetails_Impl)authentication.getPrincipal()).getID());
    }

    public List<Artist> getAllArtists() {
        return musicDAO.getAllArtists();
    }

    // find songs from all songs
    public List<Song> findSongs(Authentication authentication, String song, String artist) {
        if (authentication == null) {return musicDAO.findSongsSearchNoAuth(song, artist);}
        return musicDAO.findSongsSearchAuth(song, artist, ((UserDetails_Impl)authentication.getPrincipal()).getID());
    }

    // finds songs from library songs
    public List<Song> findSongsLibrary(int id, String song, String artist) {
        return musicDAO.findSongsLibrary(id, song, artist);
    }

    // finds albums from all albums
    public List<Album> findAlbums(Authentication authentication, String album, String artist) {
        if (authentication == null) {return musicDAO.findAlbumsNoAuth(album, artist);}
        return musicDAO.findAlbumsAuth(((UserDetails_Impl)authentication.getPrincipal()).getID(), album, artist);
    }

    public List<Artist> findArtists(String artist, String album) {
        return musicDAO.findArtists(artist, album);
    }

    public Song getSongByID(int id, Authentication authentication) {
        if (authentication == null) {
            return musicDAO.getSongNoAuth(id);
        }
        return musicDAO.getSongAuth(id, ((UserDetails_Impl)authentication.getPrincipal()).getID());
    }

    public Artist getArtistByID(int id) {
        return musicDAO.getArtist(id);
    }

    public List<Album> getAlbumsOfArtistByID(int id, Authentication authentication) {
        if (authentication == null) {
            return musicDAO.getAlbumsOfArtistNoAuth(id);
        }
        return musicDAO.getAlbumsOfArtistAuth(id, ((UserDetails_Impl)authentication.getPrincipal()).getID());
    }

    public Album getAlbum(int id, Authentication authentication) {
        if (authentication == null) {
            return musicDAO.getAlbumNoAuth(id);
        }
        return musicDAO.getAlbumAuth(id, ((UserDetails_Impl)authentication.getPrincipal()).getID());
    }
}
