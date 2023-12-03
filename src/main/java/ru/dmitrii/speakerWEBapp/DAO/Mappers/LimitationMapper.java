package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Limitation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LimitationMapper implements RowMapper<Limitation> {
    @Override
    public Limitation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Limitation limitation = new Limitation();

        limitation.setId(rs.getInt("idlimitation"));
        limitation.setValue(rs.getInt("limval"));
        limitation.setName(rs.getString("limname"));

        return limitation;
    }
}
