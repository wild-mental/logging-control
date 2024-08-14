package ac.su.kdt.loggingcontrol.logger;

import ac.su.kdt.loggingcontrol.controller.LogController;
import ac.su.kdt.loggingcontrol.util.CustomIpUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

public class CustomLogger {
    private static final Logger logger = LogManager.getLogger(LogController.class);

    public static void logRequest(
        String logType,
        String url,
        String method,
        String userId,
        String transactionId,
        String productId,
        String cartId,
        String orderId,
        String payload,
        HttpServletRequest request
    ) {
        logger.info(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
            logType,  // 로그 타입을 맨 앞에 두어서 로그 타입에 따라 필터링이 용이 하도록 함
            LocalDateTime.now(),  // 로그 시간은 파라미터 수신할 필요가 없으므로 로그 메서드 내에서 생성
            url,
            method,
            userId,
            transactionId,
            productId,
            cartId,
            orderId,
            payload,
            CustomIpUtil.getClientIp(request),
            request.getHeader("User-Agent"),
            request.getHeader("Referer")
        ));
    }

    // @Scheduled(cron = "*/10 * * * * *")
    public static void scheduledLogRotate() {
        // 로그 파일을 커스텀 로직으로 회전하는 작업을 수행하는 메서드
    }
}