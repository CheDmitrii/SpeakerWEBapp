package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Album;
import ru.dmitrii.speakerWEBapp.models.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AllAlbumsMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();

        album.setId(rs.getInt("idAlbum"));
        album.setName(rs.getString("albumname"));

        int count = rs.getInt("count"), i = 0;
        List<Artist> list = new LinkedList<>();

        do {
            Artist artist = new Artist();
            artist.setPseudonym(rs.getString("singername"));
            artist.setId(rs.getInt("idsinger"));

            list.add(artist);
            i++;
        } while (i < count && rs.next());

        album.setArtists(list);

        return album;
    }
}
