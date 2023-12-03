package ru.dmitrii.speakerWEBapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.dmitrii.speakerWEBapp.models.User;

import java.util.Collection;

public class UserDetails_Impl implements UserDetails {

    private final User user;

    public UserDetails_Impl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    public int getID() {
        return this.user.getId();
    }

    public User getUser() {return user;}

    public int getLimvalue() {return this.user.getLimitValue();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
