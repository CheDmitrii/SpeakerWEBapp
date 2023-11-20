package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Artist;
import ru.dmitrii.speakerWEBapp.models.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SongOfAlbumMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        Song song = new Song();

        int count = rs.getInt("count"), i = 0;

        song.setId(rs.getInt("idSong"));
        song.setName(rs.getString("songname"));
        song.setText(rs.getString("songtext"));
        song.setIdAlbum(rs.getInt("idalbum"));

        List<Artist> main = new LinkedList<>(), sub = new LinkedList<>();

        // TODO: handler if count == 0

        do {
            Artist artist = new Artist();

            artist.setId(rs.getInt("idsinger"));
            artist.setPseudonym(rs.getString("singername"));

            if (rs.getBoolean("ismain")) {main.add(artist);}
            else {sub.add(artist);}

            i++;
        } while (i < count && rs.next());

        song.setArtists(main);
        song.setSubArtists(sub);

        return song;
    }
}
