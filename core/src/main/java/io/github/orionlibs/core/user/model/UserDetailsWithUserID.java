package io.github.orionlibs.core.user.model;

import java.util.Collection;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsWithUserID
{
    private String userID;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;


    public UserDetailsWithUserID(String userID, UserDetails user)
    {
        this.userID = userID;
        this.username = user.getUsername();
        this.password = getPassword();
        this.authorities = user.getAuthorities();
        this.enabled = user.isEnabled();
        this.accountNonLocked = user.isAccountNonLocked();
        this.accountNonExpired = user.isAccountNonExpired();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
    }


    public String getUserID()
    {
        return userID;
    }


    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public boolean isAccountNonExpired()
    {
        return accountNonExpired;
    }


    public boolean isAccountNonLocked()
    {
        return accountNonLocked;
    }


    public boolean isCredentialsNonExpired()
    {
        return credentialsNonExpired;
    }


    public boolean isEnabled()
    {
        return enabled;
    }
}
