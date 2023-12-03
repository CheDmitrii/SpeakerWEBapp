package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.Subscribe;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscribeMapper implements RowMapper<Subscribe> {
    @Override
    public Subscribe mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subscribe subscribe = new Subscribe();

        subscribe.setIdSubscribe(rs.getInt("idsubscribe"));
        subscribe.setCost(rs.getDouble("subscribecost"));
        subscribe.setName(rs.getString("subscribename"));

        return subscribe;
    }
}
