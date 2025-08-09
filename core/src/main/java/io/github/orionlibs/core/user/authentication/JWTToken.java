package io.github.orionlibs.core.user.authentication;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JWTToken
{
    private String token;
    private String userID;
    //private String authorities;
    private LocalDateTime tokenExpirationDateTime;
    //private String refreshToken;
    private LocalDateTime refreshTokenExpirationDateTime;
    private boolean expiredToken;
    private boolean expiredRefreshToken;
    private boolean doesRefreshTokenExistInDatabase;
    private boolean hasErrors;
}
