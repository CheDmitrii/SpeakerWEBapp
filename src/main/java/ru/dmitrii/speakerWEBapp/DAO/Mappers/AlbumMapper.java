package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Album;
import ru.dmitrii.speakerWEBapp.models.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AlbumMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();

        album.setId(rs.getInt("idAlbum"));
        album.setName(rs.getString("albumname"));
        album.setLabel(rs.getString("labelname"));
        album.setIsadd(rs.getBoolean("isadd"));
        album.setLimVal(rs.getInt("limval"));

        int count = rs.getInt("count"), index = 0;

        List<Artist> artists = new LinkedList<>();

        do {
            Artist artist = new Artist();

            artist.setId(rs.getInt("idsinger"));
            artist.setPseudonym(rs.getString("singername"));

            artists.add(artist);
            index++;
        } while (index < count && rs.next());

        album.setArtists(artists);

        return album;
    }
}
