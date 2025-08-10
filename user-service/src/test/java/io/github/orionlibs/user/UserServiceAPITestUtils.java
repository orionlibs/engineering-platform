package io.github.orionlibs.user;

import io.github.orionlibs.core.tests.APITestUtils;
import io.github.orionlibs.core.tests.JWTBuilderForTests;
import io.github.orionlibs.user.api.key.APIKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceAPITestUtils extends APITestUtils implements JWTBuilderForTests
{
    @Autowired APIKeyService apiKeyService;


    @Override
    public String jwtWithAuthorities(String base64Secret, String subject, String... authorities)
    {
        String apiKey = super.jwtWithAuthorities(base64Secret, subject, authorities);
        apiKeyService.save(subject, apiKey, "");
        return apiKey;
    }
}
