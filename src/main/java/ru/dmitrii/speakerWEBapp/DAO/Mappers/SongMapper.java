package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Artist;
import ru.dmitrii.speakerWEBapp.models.Song;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SongMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException {
        Song song = new Song();

        int count = rs.getInt("count"), index = 0;

        song.setId(rs.getInt("idSong"));
        song.setName(rs.getString("songname"));
        song.setText(rs.getString("songtext"));
        song.setIdAlbum(rs.getInt("idalbum"));
        song.setAdd(rs.getBoolean("isadd"));
        song.setLimValue(rs.getInt("limval"));

        List<Artist> primary = new LinkedList<>(), sub = new LinkedList<>();

        do {
            Artist artist = new Artist();
            artist.setId(rs.getInt("idsinger"));
            artist.setPseudonym(rs.getString("singername"));

            if (rs.getBoolean("ismain")) {primary.add(artist);}
            else {sub.add(artist);}

            index++;
        } while (index < count && rs.next());

        song.setArtists(primary);
        song.setSubArtists(sub);

        return song;
    }

    public List<Integer> getList(char[] chars) {
        List<Integer> list = new LinkedList<>();
        int id, i = 0, len = chars.length;

        while (i < len) {
            int index = i;
            id = 0;
            while (index < len && chars[index] != ',') {
                id *= 10;
                id += chars[index] - '0';
                index++;
            }
            while (index < len && chars[index] == ',') {index++;}
            i = index;
            list.add(id);
        }
        return list;
    }
}
