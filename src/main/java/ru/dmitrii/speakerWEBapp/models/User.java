package ru.dmitrii.speakerWEBapp.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Component
public class User {
    private int id;
    @NotEmpty
//    @Length(min = 5, message = "{error.registration.username.length}")
    @Size(min = 5, message = "{error.registration.username.length}")
    private String username;
    @NotEmpty(message = "{error.registration.firstname.length}")
    private String firstName;
    @NotEmpty(message = "{error.registration.firstname.length}")
    private String secondName;
    private String password;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthday;
    private String limName;
    private int limitValue;
    private int idLim;
    private int idSubscribe;
    private double subscribeCost;
    private String subscribeName;

    public User() {}
    public User(int id, String username, String firstName, String secondName, String password, Date birthday,
                String limName, int limitValue, int idLim, int idSubscribe, double subscribeCost, String subscribeName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.birthday = birthday;
        this.limName = limName;
        this.limitValue = limitValue;
        this.idLim = idLim;
        this.idSubscribe = idSubscribe;
        this.subscribeCost = subscribeCost;
        this.subscribeName = subscribeName;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String userName) {
        this.username = userName;
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

    public String getLimName() {
        return limName;
    }
    public void setLimName(String limname) {
        this.limName = limname;
    }

    public int getLimitValue() {
        return limitValue;
    }
    public void setLimitValue(int limitValue) {
        this.limitValue = limitValue;
    }

    public int getIdLim() {
        return idLim;
    }
    public void setIdLim(int idLim) {
        this.idLim = idLim;
    }

    public int getIdSubscribe() {
        return idSubscribe;
    }

    public void setIdSubscribe(int idSubscribe) {
        this.idSubscribe = idSubscribe;
    }

    public double getSubscribeCost() {
        return subscribeCost;
    }

    public void setSubscribeCost(double subscribeCost) {
        this.subscribeCost = subscribeCost;
    }

    public String getSubscribeName() {
        return subscribeName;
    }

    public void setSubscribeName(String subscribeName) {
        this.subscribeName = subscribeName;
    }

    public String getBirthdayString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        format.setLenient(false);
        return format.format(this.birthday);
    }
    public void updateUser(User updated) {
        this.username = updated.username;
        this.firstName = updated.firstName;
        this.secondName = updated.secondName;
        this.birthday = updated.birthday;
        this.idLim = updated.idLim;
        this.limitValue = updated.limitValue;
        this.limName = updated.limName;
        this.idSubscribe = updated.idSubscribe;
        this.subscribeCost = updated.subscribeCost;
        this.subscribeName = updated.subscribeName;
        this.password = updated.password;
    }
}
