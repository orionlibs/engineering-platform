package io.github.orionlibs.core.api.header;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class HeaderServiceTest
{
    @Autowired HeaderService headerService;


    @Test
    void convertSigningKeyToSecretKeyObject()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HTTPHeader.Authorization.get(), HTTPHeaderValue.Bearer.get() + "mytoken");
        String token = headerService.extractAuthorisationBearerToken(request);
        assertThat(token).isEqualTo("mytoken");
    }
}
