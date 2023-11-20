package ru.dmitrii.speakerWEBapp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUser(String username){
        return jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * FROM \"User\" WHERE username=?);",
                new Object[]{username}, Boolean.class);
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

    public void createUser(String firstName, String secondName, String password) {
    }

    public void changePassword(int id, String newpassword) {
        jdbcTemplate.update("UPDATE \"User\"\n" +
                "SET password=?\n" +
                "WHERE iduser=?;",
                newpassword, id);
    }
}
