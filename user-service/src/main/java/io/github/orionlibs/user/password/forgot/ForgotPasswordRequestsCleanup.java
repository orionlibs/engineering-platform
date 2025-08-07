package io.github.orionlibs.user.password.forgot;

import io.github.orionlibs.user.password.forgot.model.ForgotPasswordRequestsDAO;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ForgotPasswordRequestsCleanup
{
    @Autowired
    private ForgotPasswordRequestsDAO dao;


    @PostConstruct
    @Transactional
    public void purgeOnStartup()
    {
        cleanUpExpiredCodes();
    }


    @Scheduled(initialDelayString = "0", fixedRateString = "${app.cleanup.expired_forgot_password_requests.rate:86400000}")
    @Transactional
    public void cleanUpExpiredCodes()
    {
        LocalDateTime now = LocalDateTime.now();
        long deleted = dao.deleteByExpiresAtBefore(now);
    }
}
