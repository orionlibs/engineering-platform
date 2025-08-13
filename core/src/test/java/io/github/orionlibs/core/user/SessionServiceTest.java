package io.github.orionlibs.core.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class SessionServiceTest
{
    @Test
    void setAttributeAndGetAttribute()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SessionService.setAttribute(request, SessionAttribute.currentUserID, "someUserID");
        Object value = SessionService.getAttribute(request, SessionAttribute.currentUserID);
        assertThat(value).isEqualTo("someUserID");
    }


    @Test
    void getUserID()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        SessionService.setAttribute(request, SessionAttribute.currentUserID, "someUserID");
        String value = SessionService.getUserID(request);
        assertThat(value).isEqualTo("someUserID");
    }
}
