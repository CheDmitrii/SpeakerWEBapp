package ru.dmitrii.speakerWEBapp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dmitrii.speakerWEBapp.DAO.Mappers.*;
import ru.dmitrii.speakerWEBapp.models.Album;
import ru.dmitrii.speakerWEBapp.models.Artist;
import ru.dmitrii.speakerWEBapp.models.Song;

import java.util.List;


@Component
public class MusicDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MusicDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Song> getAllSongsNoAuth() {
        return jdbcTemplate.query("SELECT song.idSong, songname, songtext, idAlbum, singer.idsinger, singername, " +
                        "singers as \"count\", ismain, false as isadd, limval FROM song\n" +
                        "        JOIN songsource ON song.idsong = songsource.idsong\n" +
                        "        JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                        "        JOIN singer ON singer.idsinger = song_singer.idsinger\n" +
                        "        JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                        "        GROUP BY idsong) as counter ON song.idsong=counter.idsong\n" +
                        "        LEFT JOIN library l on song.idsong = l.idsong\n" +
                        "        GROUP BY song.idsong, songtext, singer.idsinger, singers, ismain\n" +
                        "        ORDER BY song.idsong;",
                new SongMapper());

    }

    public List<Song> getAllSongsAuth(int id) {
        return jdbcTemplate.query("SELECT song.idSong, songname, songtext, idAlbum, singer.idsinger, singername, " +
                        "singers as \"count\", ismain, count(iduser) FILTER(WHERE iduser=?) > 0 as isadd, limval FROM song\n" +
                        "        JOIN songsource ON song.idsong = songsource.idsong\n" +
                        "        JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                        "        JOIN singer ON singer.idsinger = song_singer.idsinger\n" +
                        "        JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                        "        GROUP BY idsong) as counter ON song.idsong=counter.idsong\n" +
                        "        LEFT JOIN library l on song.idsong = l.idsong\n" +
                        "        GROUP BY song.idsong, songtext, singer.idsinger, singers, ismain\n" +
                        "        ORDER BY song.idsong;",
                new Object[]{id}, new SongMapper());


        // TODO: change for user (isadd - ready)
    }

    public Song getSongNoAuth(int id) {
        return jdbcTemplate.queryForObject("SELECT Song.idSong, songname, songtext, idAlbum, false as isadd,\n" +
                "singer.idsinger, singername, ismain, counter.singers as count, limval FROM song\n" +
                "        LEFT JOIN library on song.idsong = library.idsong\n" +
                "        JOIN songsource ON song.idsong = songsource.idsong\n" +
                "        JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                "        JOIN singer ON song_singer.idsinger = singer.idsinger\n" +
                "        JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                "        GROUP BY idsong) as counter ON counter.idsong=song.idsong\n" +
                "        WHERE song.idSong=?\n" +
                "        GROUP BY song.idsong, songtext, singer.idsinger, ismain, singers;", new Object[]{id}, new SongMapper());
    }

    public Song getSongAuth(int idSong, int idUser) {
        return jdbcTemplate.queryForObject("SELECT Song.idSong, songname, songtext, idAlbum, " +
                        "count(iduser) FILTER (WHERE iduser=?) > 0 isadd, \n" +
                        "singer.idsinger, singername, ismain, counter.singers as count, limval FROM song\n" +
                        "        LEFT JOIN library on song.idsong = library.idsong\n" +
                        "        JOIN songsource ON song.idsong = songsource.idsong\n" +
                        "        JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                        "        JOIN singer ON song_singer.idsinger = singer.idsinger\n" +
                        "        JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                        "        GROUP BY idsong) as counter ON counter.idsong=song.idsong\n" +
                        "        WHERE song.idSong=?\n" +
                        "        GROUP BY song.idsong, songtext, singer.idsinger, ismain, singers;",
                new Object[]{idUser, idSong}, new SongMapper());
    }

    public List<Artist> getAllArtists() {
        return jdbcTemplate.query("SELECT idsinger, singername, extract(YEAR FROM age(singerbithday)) AS age, " +
                "countryname, singerbithday as bithday\n" +
                "FROM singer\n" +
                "JOIN country c on singer.idcountry = c.idcountry;", new ArtistMapper());
    }

    public Artist getArtist(int id) {
        return jdbcTemplate.queryForObject("SELECT idSinger, singername, countryname, " +
                        "extract(YEAR FROM age(singerbithday)) AS age, to_char(singerbithday, 'dd.mm.yyyy') AS bithday " +
                        "FROM singer, country WHERE singer.idcountry=country.idcountry AND idsinger=?",
                new Object[]{id}, new ArtistMapper());
    }

    public List<Album> getAllAlbumsNoAuth(){
        return jdbcTemplate.query("SELECT album.idalbum, albumname, s.idsinger, singername, labelname, " +
                "help.count, false as isadd, al_s.limval  FROM album\n" +
                "JOIN label l on l.idlabel = album.idlabel\n" +
                "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                "    JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                "    GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                "JOIN (SELECT album.idalbum, max(limval) as limval FROM album, song\n" +
                "    WHERE album.idalbum = song.idalbum\n" +
                "    GROUP BY album.idalbum) as al_s on al_s.idalbum=album.idalbum\n" +
                "GROUP BY album.idalbum, s.idsinger, help.count, labelname, al_s.limval\n" +
                "ORDER BY album.idalbum;", new AlbumMapper());
    }

    public List<Album> getAllAlbumsAuth(int id) {
        return jdbcTemplate.query("SELECT album.idalbum, albumname, s.idsinger, singername, labelname, help.count,\n" +
                "al_s.counter=us.counter as isadd, al_s.limval  FROM album\n" +
                "JOIN label l on l.idlabel = album.idlabel\n" +
                "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                "    JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                "    GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                "JOIN (SELECT album.idalbum, count(idsong) as counter, max(limval) as limval FROM album, song\n" +
                "    WHERE album.idalbum = song.idalbum\n" +
                "    GROUP BY album.idalbum) as al_s on al_s.idalbum=album.idalbum\n" +
                "JOIN (SELECT idalbum, count(l.iduser) FILTER (WHERE iduser=?) as counter FROM song\n" +
                "    LEFT JOIN library l on song.idsong = l.idsong\n" +
                "    GROUP BY idalbum) as us on us.idalbum=album.idalbum\n" +
                "GROUP BY album.idalbum, s.idsinger, help.count, al_s.counter, us.counter, labelname, al_s.limval\n" +
                "ORDER BY album.idalbum;", new Object[]{id}, new AlbumMapper());
    }

    public Album getAlbumNoAuth(int id) {
        //ready
        Album album = jdbcTemplate.queryForObject("SELECT album.idalbum, albumname, s.idsinger, singername, labelname,\n" +
                        "help.count, false as isadd, al_s.limval  FROM album\n" +
                        "JOIN label l on l.idlabel = album.idlabel\n" +
                        "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                        "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                        "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                        "    JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                        "    GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                        "JOIN (SELECT album.idalbum, max(limval) as limval FROM album, song\n" +
                        "    WHERE album.idalbum = song.idalbum\n" +
                        "    GROUP BY album.idalbum) as al_s on al_s.idalbum=album.idalbum\n" +
                        "WHERE album.idalbum=?\n" +
                        "GROUP BY album.idalbum, s.idsinger, help.count, labelname, al_s.limval\n" +
                        "ORDER BY album.idalbum;",
                new Object[]{id}, new AlbumMapper());

        // ready
        album.setSongs(jdbcTemplate.query("SELECT song.idsong, songname, '' as songtext, idalbum, " +
                "s.idsinger, singername, \"count\", ismain, false as isadd, limval FROM song\n" +
                "JOIN song_singer ss on song.idsong = ss.idsong\n" +
                "JOIN singer s on ss.idsinger = s.idsinger\n" +
                "JOIN (SELECT song.idsong, count(idsinger) as \"count\" FROM song\n" +
                "   JOIN song_singer ss on song.idsong = ss.idsong\n" +
                "   GROUP BY song.idsong) as cnt on song.idsong=cnt.idsong\n" +
                "WHERE song.idalbum=?\n" +
                "ORDER BY song.idsong;", new Object[]{id}, new SongMapper()));

        return album;
    }

    public Album getAlbumAuth(int idAlbum, int idUser) {
        Album album =  jdbcTemplate.queryForObject("SELECT album.idalbum, albumname, s.idsinger, singername, labelname, help.count,\n" +
                "al_s.counter=us.counter as isadd, al_s.limval  FROM album\n" +
                        "JOIN label l on l.idlabel = album.idlabel\n" +
                        "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                        "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                        "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                        "    JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                        "    GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                        "JOIN (SELECT album.idalbum, count(idsong) as counter, max(limval) as limval FROM album, song\n" +
                        "    WHERE album.idalbum = song.idalbum\n" +
                        "    GROUP BY album.idalbum) as al_s on al_s.idalbum=album.idalbum\n" +
                        "JOIN (SELECT idalbum, count(l.iduser) FILTER (WHERE iduser=?) as counter FROM song\n" +
                        "    LEFT JOIN library l on song.idsong = l.idsong\n" +
                        "    GROUP BY idalbum) as us on us.idalbum=album.idalbum\n" +
                        "WHERE album.idalbum=?" +
                        "GROUP BY album.idalbum, s.idsinger, help.count, al_s.counter, us.counter, labelname, al_s.limval\n" +
                        "ORDER BY album.idalbum;",
                new Object[]{idUser, idAlbum}, new AlbumMapper());

        album.setSongs(jdbcTemplate.query("SELECT song.idsong, songname, '' as songtext, idalbum, s.idsinger, " +
                "singername, \"count\", ismain, count(iduser) FILTER (WHERE iduser=?) > 0 as isadd, limval FROM song\n" +
                "JOIN song_singer ss on song.idsong = ss.idsong\n" +
                "JOIN singer s on ss.idsinger = s.idsinger\n" +
                "JOIN (SELECT song.idsong, count(idsinger) as \"count\" FROM song\n" +
                "   JOIN song_singer ss on song.idsong = ss.idsong\n" +
                "   GROUP BY song.idsong) as cnt on song.idsong=cnt.idsong\n" +
                "LEFT JOIN library l on song.idsong = l.idsong\n" +
                "WHERE song.idalbum=?\n" +
                "GROUP BY song.idsong, s.idsinger, \"count\", ismain, limval\n" +
                "ORDER BY song.idsong;", new Object[]{idUser, idAlbum}, new SongMapper()));

        return album;
    }

    public List<Album> getAlbumsOfArtistNoAuth(int id) {
        return jdbcTemplate.query("SELECT album.idalbum, albumname, limval FROM album\n" +
                        "JOIN (SELECT idalbum, count(*) as counter FROM song\n" +
                        "    GROUP BY idalbum) as songs on songs.idalbum=album.idalbum\n" +
                        "JOIN (SELECT idalbum, max(limval) as limval FROM song\n" +
                        "      GROUP BY idalbum) as values on songs.idalbum=values.idalbum\n" +
                        "WHERE album.idalbum IN (SELECT idalbum FROM song_singer\n" +
                        "    JOIN song s on s.idsong = song_singer.idsong\n" +
                        "    WHERE idsinger=?\n" +
                        "    GROUP BY idalbum, s.idsong);", new Object[]{id},
                (rs, rowNum) -> {
            Album album = new Album();

            album.setId(rs.getInt("idalbum"));
            album.setName(rs.getString("albumname"));
            album.setLimVal(rs.getInt("limval"));
            album.setIsadd(false);

            return album;
        });
    }
    public List<Album> getAlbumsOfArtistAuth(int idUser, int idArtist) {
        return jdbcTemplate.query("SELECT album.idalbum, albumname, songs.counter=adds.added as isadd, limval FROM album\n" +
                        "JOIN (SELECT idalbum, count(*) as counter FROM song\n" +
                        "    GROUP BY idalbum) as songs on songs.idalbum=album.idalbum\n" +
                        "JOIN (SELECT idalbum, count(iduser) FILTER (WHERE iduser=?) as added FROM song\n" +
                        "    LEFT JOIN library l on song.idsong = l.idsong\n" +
                        "    GROUP BY idalbum) as adds on adds.idalbum=album.idalbum\n" +
                        "JOIN (SELECT idalbum, max(limval) as limval FROM song\n" +
                        "      GROUP BY idalbum) as values on songs.idalbum=values.idalbum\n" +
                        "WHERE album.idalbum IN (SELECT idalbum FROM song_singer\n" +
                        "    JOIN song s on s.idsong = song_singer.idsong\n" +
                        "    WHERE idsinger=?\n" +
                        "    GROUP BY idalbum, s.idsong);", new Object[]{idUser, idArtist},
                (rs, rowNum) -> {
                    Album album = new Album();

                    album.setId(rs.getInt("idalbum"));
                    album.setName(rs.getString("albumname"));
                    album.setLimVal(rs.getInt("limval"));
                    album.setIsadd(rs.getBoolean("isadd"));

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


    // USER'S OPERATIONS WITH MUSIC (add and delete) move to userDAO


    // return songs from library (songs that user add)
    public List<Song> getUserSongs(int id) {
        return jdbcTemplate.query("SELECT library.idsong, songname, songtext, s3.idsinger,\n" +
                "singername, ismain, idalbum, counter as \"count\", limval, true as isadd FROM library\n" +
                "LEFT JOIN song s on s.idsong = library.idsong\n" +
                "JOIN song_singer ss on s.idsong = ss.idsong\n" +
                "JOIN songsource s2 on s.idsong = s2.idsong\n" +
                "JOIN singer s3 on s3.idsinger = ss.idsinger\n" +
                "JOIN (SELECT idsong, count(*) as counter FROM song_singer\n" +
                "      GROUP BY idsong) as singers on library.idsong=singers.idsong\n" +
                "WHERE iduser=?\n" +
                "ORDER BY library.idsong;", new Object[]{id}, new SongMapper());
    }

    // return list of songs that finds in search page no authenticated user
    public List<Song> findSongsSearchNoAuth(String song, String artist) {
        return jdbcTemplate.query("SELECT song.idSong, songname, songtext, idAlbum, singer.idsinger, singername,\n" +
                "singers as \"count\", ismain, false as isadd, limval FROM song\n" +
                "JOIN songsource ON song.idsong = songsource.idsong\n" +
                "JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                "JOIN singer ON singer.idsinger = song_singer.idsinger\n" +
                "JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                "    GROUP BY idsong) as counter ON song.idsong=counter.idsong\n" +
                "LEFT JOIN library l on song.idsong = l.idsong\n" +
                "WHERE songname~*concat('.*', ?, '.*') AND singername~*concat('.*', ?, '.*')\n" +
                "GROUP BY song.idsong, songtext, singer.idsinger, singers, ismain\n" +
                "ORDER BY song.idsong;", new Object[]{song, artist}, new SongMapper());
    }

    // return list of songs that finds in search page authenticated user
    public List<Song> findSongsSearchAuth(String song, String artist, int id) {
        return jdbcTemplate.query("SELECT song.idSong, songname, songtext, idAlbum, singer.idsinger, singername,\n" +
                "singers as \"count\", ismain, count(iduser) FILTER(WHERE iduser=?) > 0 as isadd, limval FROM song\n" +
                "JOIN songsource ON song.idsong = songsource.idsong\n" +
                "JOIN song_singer ON song.idsong = song_singer.idsong\n" +
                "JOIN singer ON singer.idsinger = song_singer.idsinger\n" +
                "JOIN (SELECT idsong, count(*) as singers FROM song_singer\n" +
                "    GROUP BY idsong) as counter ON song.idsong=counter.idsong\n" +
                "LEFT JOIN library l on song.idsong = l.idsong\n" +
                "WHERE songname~*concat('.*', ?, '.*') AND singername~*concat('.*', ?, '.*')\n" +
                "GROUP BY song.idsong, songtext, singer.idsinger, singers, ismain\n" +
                "ORDER BY song.idsong;", new Object[]{id, song, artist}, new SongMapper());
    }

    // return list of songs that finds in library page authenticated user
    public List<Song> findSongsLibrary(int id, String song, String artist) {
        return jdbcTemplate.query("SELECT library.idsong, songname, songtext, s3.idsinger,\n" +
                "singername, ismain, idalbum, counter as \"count\", limval, true as isadd FROM library\n" +
                "LEFT JOIN song s on s.idsong = library.idsong\n" +
                "JOIN song_singer ss on s.idsong = ss.idsong\n" +
                "JOIN songsource s2 on s.idsong = s2.idsong\n" +
                "JOIN singer s3 on s3.idsinger = ss.idsinger\n" +
                "JOIN (SELECT idsong, count(*) as counter FROM song_singer\n" +
                "      GROUP BY idsong) as singers on library.idsong=singers.idsong\n" +
                "WHERE iduser=? AND songname~*concat('.*', ?, '.*') AND singername~*concat('.*', ?, '.*')\n" +
                "ORDER BY library.idsong;", new Object[]{id, song, artist}, new SongMapper());
    }

    // return albums that find no authenticated user on library page
    public List<Album> findAlbumsNoAuth(String album, String artist) {
        return jdbcTemplate.query("SELECT album.idalbum, albumname, s.idsinger, singername, labelname,\n" +
                "help.count, false as isadd, al_s.limval  FROM album\n" +
                "JOIN label l on l.idlabel = album.idlabel\n" +
                "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                "    JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                "    GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                "JOIN (SELECT album.idalbum, max(limval) as limval FROM album, song\n" +
                "    WHERE album.idalbum = song.idalbum\n" +
                "    GROUP BY album.idalbum) as al_s on al_s.idalbum=album.idalbum\n" +
                "WHERE albumname~*concat('.*', ?, '.*') AND singername~*concat('.*', ?, '.*')\n" +
                "GROUP BY album.idalbum, s.idsinger, help.count, labelname, al_s.limval\n" +
                "ORDER BY album.idalbum;", new Object[]{album, artist}, new AlbumMapper());
    }

    // return albums that find authenticated user on library page
    public List<Album> findAlbumsAuth(int id, String album, String artist) {
        return jdbcTemplate.query("SELECT album.idalbum, albumname, s.idsinger, singername, labelname, help.count,\n" +
                "al_s.counter=us.counter as isadd, al_s.limval  FROM album\n" +
                "JOIN label l on l.idlabel = album.idlabel\n" +
                "JOIN album_singer a_s on album.idalbum = a_s.idalbum\n" +
                "JOIN singer s on s.idsinger = a_s.idsinger\n" +
                "JOIN (SELECT idalbum, count(singer.idsinger) as \"count\" FROM singer\n" +
                "    JOIN album_singer a_s on singer.idsinger = a_s.idsinger\n" +
                "    GROUP BY idalbum) as help on help.idalbum=album.idalbum\n" +
                "JOIN (SELECT album.idalbum, count(idsong) as counter, max(limval) as limval FROM album, song\n" +
                "    WHERE album.idalbum = song.idalbum\n" +
                "    GROUP BY album.idalbum) as al_s on al_s.idalbum=album.idalbum\n" +
                "JOIN (SELECT idalbum, count(l.iduser) FILTER (WHERE iduser=1) as counter FROM song\n" +
                "    LEFT JOIN library l on song.idsong = l.idsong\n" +
                "    GROUP BY idalbum) as us on us.idalbum=album.idalbum\n" +
                "WHERE albumname~*concat('.*', '', '.*') AND singername~*concat('.*', ?, '.*')\n" +
                "GROUP BY album.idalbum, s.idsinger, help.count, al_s.counter, us.counter, labelname, al_s.limval\n" +
                "ORDER BY album.idalbum;", new Object[]{id, album, artist}, new AlbumMapper());
    }

    public List<Artist> findArtists(String artist, String album) {
        return jdbcTemplate.query("SELECT DISTINCT singer.idsinger, singer.singername, " +
                "extract(YEAR FROM age(singerbithday)) as age, countryname, singerbithday as bithday FROM singer\n" +
                "JOIN country c on c.idcountry = singer.idcountry\n" +
                "JOIN song_singer ss on singer.idsinger = ss.idsinger\n" +
                "JOIN song s on s.idsong = ss.idsong\n" +
                "JOIN album a on s.idalbum = a.idalbum\n" +
                "WHERE singername~*concat('.*', ?, '.*') AND albumname~*concat('.*', ?, '.*');",
                new Object[]{artist, album}, new ArtistMapper());
    }

    public void addSongMusic(int idUser, int idSong) {
        jdbcTemplate.update("INSERT INTO library(iduser, idsong) VALUES (?, ?)", idUser, idSong);
    }
}