package io.github.orionlibs.core.user.authentication;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JWTToken
{
    private String token;
    private String userID;
    private Collection<? extends GrantedAuthority> authorities;
    private LocalDateTime tokenExpirationDateTime;
    //private String refreshToken;
    //private LocalDateTime refreshTokenExpirationDateTime;
    private boolean expiredToken;
    //private boolean expiredRefreshToken;
    //private boolean doesRefreshTokenExistInDatabase;
    private boolean hasErrors;
}
