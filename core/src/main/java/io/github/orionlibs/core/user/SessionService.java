package io.github.orionlibs.core.user;

import jakarta.servlet.http.HttpServletRequest;

public class SessionService
{
    public static Object getAttribute(HttpServletRequest request, String attributeName)
    {
        return request.getAttribute(attributeName);
    }


    public static String getUserID(HttpServletRequest request)
    {
        return (String)getAttribute(request, SessionAttribute.currentUserID);
    }


    public static void setAttribute(HttpServletRequest request, String attributeName, Object attributeValue)
    {
        request.setAttribute(attributeName, attributeValue);
    }
}
