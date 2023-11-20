package ru.dmitrii.speakerWEBapp.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

//import java.sql.Date;
import java.time.LocalDate;
import java.util.Date;

@Component
public class User {
    private int id;
    @NotEmpty
    @Length(min = 5, message = "Length should start from 5")
//    @Size(min = 5, message = "Length should start from 5")
    private String userName;
    @NotEmpty(message = "First name shouldn't be empty")
    private String firstName;
    @NotEmpty(message = "Second name shouldn't be empty")
    private String secondName;
    private String password;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthday;
    private LocalDate datedate;
    private int limitValue;

    public User() {
    }

    public User(int id, String userName, String firstName, String secondName, String password, Date bithday, int limitValue) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.birthday = bithday;
        this.limitValue = limitValue;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public LocalDate getDatedate() {
        return datedate;
    }
    public void setDatedate(LocalDate datedate) {
        this.datedate = datedate;
    }

    public int getLimitValue() {
        return limitValue;
    }
    public void setLimitValue(int limitValue) {
        this.limitValue = limitValue;
    }
}
