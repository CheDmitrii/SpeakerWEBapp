package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Album;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        Album album = new Album();

        album.setId(rs.getInt("idAlbum"));
        album.setName(rs.getString("albumname"));
        album.setLabel(rs.getString("labelname"));

        return album;
    }
}
