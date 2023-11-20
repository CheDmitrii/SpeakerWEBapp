package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Artist;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistMapper implements RowMapper<Artist> {
    @Override
    public Artist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Artist artist = new Artist();

        artist.setId(rs.getInt("idsinger"));
        artist.setPseudonym(rs.getString("singername"));
        artist.setAge(rs.getInt("age"));
        artist.setBithday(rs.getString("bithday"));
        artist.setCountry(rs.getString("countryname"));

        return artist;
    }
}
