package ru.dmitrii.speakerWEBapp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.dmitrii.speakerWEBapp.DAO.Mappers.LimitationMapper;
import ru.dmitrii.speakerWEBapp.DAO.Mappers.SubscribeMapper;
import ru.dmitrii.speakerWEBapp.DAO.Mappers.UserMapper;
import ru.dmitrii.speakerWEBapp.models.Limitation;
import ru.dmitrii.speakerWEBapp.models.Subscribe;
import ru.dmitrii.speakerWEBapp.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int getUserIDByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT iduser FROM \"User\" WHERE username=?",
                new Object[]{username}, Integer.class);
    }
    public User getUser(String username) {
        return jdbcTemplate.queryForObject("SELECT iduser as id, username, firstname, secondname,\n" +
                "userbithday as bithday, password, limval, limname, l.idlimitation, s.idsubscribe, \n" +
                "subscribename, subscribecost FROM \"User\"\n" +
                "JOIN limitation l on l.idlimitation = \"User\".idlimitation\n" +
                "JOIN subscribe s on s.idsubscribe = \"User\".idsubscribe\n" +
                "WHERE username=?;", new Object[]{username}, new UserMapper());
    }
    public boolean isUser(String username){
        // SELECT EXISTS(SELECT * FROM "User" WHERE username~*?);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject("SELECT is_exist(?);",
                new Object[]{username}, Boolean.class));
    }

    public boolean checkTakenUpdateUsername(String newUserName, int id) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT iduser FROM \"User\" " +
                "WHERE username~*? AND iduser!=?);", new Object[]{newUserName, id}, Boolean.class);
    }
    public boolean rightPassword(String username, String password) {
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM \"User\"" +
                        "WHERE username=? AND password=?)",
                new Object[]{username, password}, Boolean.class);
    }

    public void setNewLimitation(int id, int limval) {
        jdbcTemplate.update("UPDATE \"User\"\n" +
                "SET idlimitation=limitation.idlimitation\n" +
                "FROM limitation\n" +
                "JOIN \"User\" U on limitation.idlimitation = U.idlimitation\n" +
                "WHERE U.iduser=? AND limval=?;",
                id, limval);
    }

    public void createUser(User user) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        format.setLenient(false);
        jdbcTemplate.update("INSERT INTO \"User\"(username, firstname, secondname, userbithday, password) \n" +
                "VALUES(?,?,?,?::date,?); ", new Object[]{user.getUsername(), user.getFirstName(),
                user.getSecondName(), format.format(user.getBirthday()), user.getPassword()});
    }

    public void updateUser(User user) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        format.setLenient(false);
        jdbcTemplate.update("UPDATE \"User\" SET username=?, firstname=?, secondname=?, " +
                "userbithday=?::date, idlimitation=?, idsubscribe=?, password=? WHERE iduser=?",
                new Object[]{user.getUsername(), user.getFirstName(), user.getSecondName(),
                        format.format(user.getBirthday()), user.getIdLim(), user.getIdSubscribe(), user.getPassword(),
                user.getId()});
    }

    public void changePassword(int id, String newPassword) { // ??? not much need
        jdbcTemplate.update("UPDATE \"User\"\n SET password=?\n WHERE iduser=?;", newPassword, id);
    }

    public String getLimName(int id) {
        return jdbcTemplate.queryForObject("SELECT limname FROM limitation WHERE idlimitation=?",
                new Object[]{id}, String.class);
    }

    public int getLimValue(int id) {
        return jdbcTemplate.queryForObject("SELECT limval FROM limitation WHERE idlimitation=?",
                new Object[]{id}, Integer.class);
    }

    public String getSubscribeName(int id) {
        return jdbcTemplate.queryForObject("SELECT subscribename FROM subscribe WHERE idsubscribe=?",
                new Object[]{id}, String.class);
    }

    public double getSubscribeCost(int id) {
        return jdbcTemplate.queryForObject("SELECT subscribecost FROM subscribe WHERE idsubscribe=?",
                new Object[]{id}, Double.class);
    }

    public List<Limitation> getAllLimitations(int id) {
        return jdbcTemplate.query("SELECT idlimitation, limval, limname FROM limitation\n" +
                        "WHERE idlimitation!=?;", new Object[]{id},new LimitationMapper());
    }

    public List<Subscribe> getAllSubscribes(int id) {
        return jdbcTemplate.query("SELECT idsubscribe, subscribecost, subscribename FROM subscribe\n" +
                "WHERE idsubscribe!=?;", new Object[]{id}, new SubscribeMapper());
    }

    public void addMusic(Integer[] songs, int id) {
        jdbcTemplate.batchUpdate("INSERT INTO library(idsong, iduser)\n" +
                        "SELECT ?, ?\n" +
                        "WHERE NOT exists(SELECT * FROM library WHERE idsong=? AND iduser=?);",
                new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, songs[i]);
                ps.setInt(2, id);
                ps.setInt(3, songs[i]);
                ps.setInt(4, id);
            }

            @Override
            public int getBatchSize() {
                            return songs.length;
                        }
        });
    }

    public void deleteMusic(Integer[] songs, int id) {
        jdbcTemplate.batchUpdate("DELETE FROM library WHERE idsong=? AND iduser=?;",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, songs[i]);
                        ps.setInt(2, id);
                    }

                    @Override
                    public int getBatchSize() {return songs.length;}
                });
    }

    public void addAlbums(Integer[] albums, int id) {
        jdbcTemplate.batchUpdate("INSERT INTO library(idsong, iduser)\n" +
                        "SELECT idsong, ? FROM song WHERE idalbum=? AND\n" +
                        "NOT exists(SELECT * FROM library WHERE song.idsong = library.idsong AND iduser=?);",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, id);
                        ps.setInt(2, albums[i]);
                        ps.setInt(3, id);
                    }

                    @Override
                    public int getBatchSize() {
                        return albums.length;
                    }
                });
    }

    public void deleteAlbums(Integer[] albums, int id) {
        jdbcTemplate.batchUpdate("DELETE FROM library WHERE iduser=? AND " +
                        "idsong IN (SELECT idsong FROM song WHERE idalbum=?);",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, id);
                        ps.setInt(2, albums[i]);
                    }

                    @Override
                    public int getBatchSize() {
                        return albums.length;
                    }
                });
    }
}
