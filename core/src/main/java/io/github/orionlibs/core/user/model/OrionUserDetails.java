package io.github.orionlibs.core.user.model;

import java.util.Collection;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class OrionUserDetails extends User
{
    private UUID userID;


    public OrionUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, authorities);
    }


    public OrionUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


    public OrionUserDetails(UUID userID, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userID = userID;
    }


    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof OrionUserDetails user)
        {
            return this.userID.equals(user.getUserID()) && super.equals(obj);
        }
        return false;
    }
}
