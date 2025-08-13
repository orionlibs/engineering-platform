package io.github.orionlibs.core.api.metric;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class APIMetricsInterceptor implements HandlerInterceptor
{
    private final MetricNumberOfAPICalls metric;


    public APIMetricsInterceptor(MetricNumberOfAPICalls metric)
    {
        this.metric = metric;
    }


    @Override
    public boolean preHandle(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler
    )
    {
        String pattern = (String)request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        if(pattern == null)
        {
            pattern = request.getRequestURI();
        }
        metric.update("endpointURL", pattern);
        return true;
    }
}
