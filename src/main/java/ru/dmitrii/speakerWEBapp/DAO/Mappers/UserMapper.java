package ru.dmitrii.speakerWEBapp.DAO.Mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.dmitrii.speakerWEBapp.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("firstname"));
        user.setSecondName(rs.getString("secondname"));
        user.setPassword(rs.getString("password"));
        user.setBirthday(rs.getDate("bithday"));
        user.setIdLim(rs.getInt("idlimitation"));
        user.setLimitValue(rs.getInt("limval"));
        user.setLimName(rs.getString("limname"));
        user.setIdSubscribe(rs.getInt("idsubscribe"));
        user.setSubscribeCost(rs.getDouble("subscribecost"));
        user.setSubscribeName(rs.getString("subscribename"));

        return user;

    }
}
