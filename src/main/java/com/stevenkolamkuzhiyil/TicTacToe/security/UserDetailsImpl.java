package com.stevenkolamkuzhiyil.TicTacToe.security;

import com.stevenkolamkuzhiyil.TicTacToe.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = this.user.getPermissionsAsList()
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        authorities.addAll(this.user.getRolesAsList()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    public void setUsername(@Email @Valid String username) {
        this.user.setEmail(username);
    }

    public String getUserReference() {
        return this.user.getUserReference();
    }

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
        return this.user.isEnabled();
    }
}
