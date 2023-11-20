package ru.dmitrii.speakerWEBapp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.dmitrii.speakerWEBapp.DAO.Mappers.*;
import ru.dmitrii.speakerWEBapp.models.Album;
import ru.dmitrii.speakerWEBapp.models.Artist;
import ru.dmitrii.speakerWEBapp.models.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Component
public class MusicDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MusicDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Song> getAllSong() {
        return jdbcTemplate.query("SELECT song.idSong, songname, songtext, idAlbum, singer.idsinger, singername, " +
                        "singers as \"count\", ismain, count(iduser) FILTER (WHERE iduser=1) > 0 as isadd FROM song\n" +
                        "        JOIN songsource ON song.idsong = songsource.idsong\n" +
                        "        JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                        "        JOIN singer ON singer.idsinger = song_singer.idsinger\n" +
                        "        JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                        "        GROUP BY idsong) as counter ON song.idsong=counter.idsong\n" +
                        "        LEFT JOIN library l on song.idsong = l.idsong\n" +
                        "        GROUP BY song.idsong, songtext, singer.idsinger, singers, ismain\n" +
                        "        ORDER BY song.idsong;",
                new SongMapper());


        // TODO: change for user
//        --add is added for user

    }

    public Song getSong(int id) {
        return jdbcTemplate.queryForObject("SELECT Song.idSong, songname, songtext, idAlbum, " +
                        "count(iduser) FILTER (WHERE iduser=1) > 0 isadd, \n" +
                        "singer.idsinger, singername, ismain, counter.singers as count FROM song\n" +
                        "        JOIN library on song.idsong = library.idsong\n" +
                        "        JOIN songsource ON song.idsong = songsource.idsong\n" +
                        "        JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                        "        JOIN singer ON song_singer.idsinger = singer.idsinger\n" +
                        "        JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                        "        GROUP BY idsong) as counter ON counter.idsong=song.idsong\n" +
                        "        WHERE song.idSong=?\n" +
                        "        GROUP BY song.idsong, songtext, singer.idsinger, ismain, singers;",
                new Object[]{id}, new SongMapper());

    }

    public List<Artist> getAllArtist() {
        return jdbcTemplate.query("SELECT idsinger, singername, extract(YEAR FROM age(singerbithday)) AS age, " +
                "countryname, singerbithday as bithday\n" +
                "FROM singer\n" +
                "JOIN country c on singer.idcountry = c.idcountry;", new ArtistMapper());
    }
    public Artist getArtist(int id) {
        return jdbcTemplate.queryForObject("SELECT idSinger, singername, countryname, " +
                        "extract(YEAR FROM age(singerbithday)) AS age, singerbithday AS bithday " +
                        "FROM singer, country WHERE singer.idcountry=country.idcountry AND idsinger=?",
                new Object[]{id}, new ArtistMapper());
    }
    public List<Album> getAllAlbums(){
        return jdbcTemplate.query("SELECT album.idalbum, albumname, s.idsinger, singername, help.count  FROM album\n" +
                "JOIN label l on l.idlabel = album.idlabel\n" +
                "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                "      JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                "      GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                "WHERE album.idalbum!=5\n" +
                "GROUP BY album.idalbum, s.idsinger, help.count\n" +
                "ORDER BY album.idalbum;", new AllAlbumsMapper());
    }
    public Album getAlbum(int id) {
        //ready
        Album album = jdbcTemplate.queryForObject("SELECT idalbum, albumname, labelname FROM album\n" +
                "JOIN label l on album.idlabel = l.idlabel\n" +
                "WHERE idalbum=?;", new Object[]{id}, new AlbumMapper());

        // ready
        album.setSongs(jdbcTemplate.query("SELECT song.idsong, songname, '' as songtext, idalbum, " +
                "s.idsinger, singername, \"count\", ismain FROM song\n" +
                "JOIN song_singer ss on song.idsong = ss.idsong\n" +
                "JOIN singer s on ss.idsinger = s.idsinger\n" +
                "JOIN (SELECT song.idsong, count(idsinger) as \"count\" FROM song\n" +
                "   JOIN song_singer ss on song.idsong = ss.idsong\n" +
                "   GROUP BY song.idsong) as cnt on song.idsong=cnt.idsong\n" +
                "WHERE song.idalbum=?\n" +
                "ORDER BY song.idsong;", new Object[]{id}, new SongOfAlbumMapper()));

        // ready
        album.setArtists(jdbcTemplate.query("SELECT singer.idSinger, singername, countryname, " +
                "extract(YEAR FROM age(singerbithday)) AS age, singerbithday AS bithday\n" +
                "FROM country, singer\n" +
                "JOIN album_singer a_s on a_s.idsinger = singer.idsinger\n" +
                "WHERE country.idcountry=singer.idcountry AND idalbum=?;", new Object[]{id}, new ArtistMapper()));

        return album;
    }
    public List<Album> getAlbumsOfArtist(int id) {
        return jdbcTemplate.query("SELECT album.idalbum, albumname FROM album\n" +
                "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                "WHERE idsinger=?;", new Object[]{id},
                (rs, rowNum) -> {
            Album album = new Album();

            album.setId(rs.getInt("idalbum"));
            album.setName(rs.getString("albumname"));

            return album;
        });
    }
    public String getPathSong(int id) {
        return jdbcTemplate.queryForObject("SELECT SongPath FROM SongSource WHERE SongSource.idSong=?;",
                new Object[]{id}, String.class);
    }
    public String getAlbumPicturePath(int id) {
        return jdbcTemplate.queryForObject("SELECT albumphoto FROM album WHERE idAlbum=?;",
                new Object[]{id}, String.class);
    }


    // USER'S OPERATIONS WITH MUSIC
    public List<Song> getUserSongs(int id) {
        return jdbcTemplate.query("SELECT library.idsong, songname, songtext, s3.idsinger, " +
                "singername, ismain, idalbum, counter FROM library\n" +
                "JOIN song s on s.idsong = library.idsong\n" +
                "JOIN song_singer ss on s.idsong = ss.idsong\n" +
                "JOIN songsource s2 on s.idsong = s2.idsong\n" +
                "JOIN singer s3 on s3.idsinger = ss.idsinger\n" +
                "JOIN (SELECT idsong, count(*) as counter FROM song_singer\n" +
                "      GROUP BY idsong) as singers on library.idsong=singers.idsong\n" +
                "WHERE iduser=1\n" +
                "ORDER BY library.idsong;", new Object[]{id}, new SongMapper());
    }

    public void addSongMusic(int idUser, int idSong) {
        jdbcTemplate.update("INSERT INTO library(iduser, idsong) VALUES (?, ?)", idUser, idSong);
    }
}