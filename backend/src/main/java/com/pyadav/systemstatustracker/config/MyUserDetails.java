package com.pyadav.systemstatustracker.config;

import java.util.Collection;
import java.util.List;

import com.pyadav.systemstatustracker.models.UserModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
    private String id;
    private String username;
    private String email;
    private List<String> systemIds;
    
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(String id, String username, String email, String password, List<String> systemIds, Collection<? extends GrantedAuthority> authorities ) {
        this.id= id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.systemIds = systemIds;
        this.authorities = authorities;
    }

    public static MyUserDetails build(UserModel user) {
        return new MyUserDetails(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getSystemIds(), null);
    }

    //Getters

    public String getId() {
        return id;
    }
    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    @Override
    public String getPassword() {
        return password;
    }

    public List<String> getSystemIds() {
        return systemIds;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyUserDetails user = (MyUserDetails) o;
        return id.equals(user.getId());
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
        return true;
    }

}
