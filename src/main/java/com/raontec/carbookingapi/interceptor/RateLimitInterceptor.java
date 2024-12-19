package com.raontec.carbookingapi.interceptor;

import com.raontec.carbookingapi.service.RateLimitService;
import com.raontec.carbookingapi.utils.ClientIpUtil;
import com.raontec.carbookingapi.utils.RateLimitResponse;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {
    public static final Long BUCKET_CAPACITY = 50L;
    public static final Long BUCKET_TOKENS = 50L;
    public static final Duration CALLS_IN_SECONDS = Duration.ofSeconds(1);
    public static final Integer REQUEST_COST_IN_TOKENS = 1;

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final RateLimitService rateLimitService = new RateLimitService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = ClientIpUtil.getClientIp(request);

        Bucket bucket = cache.computeIfAbsent(clientIp, key -> newBucket());
        ConsumptionProbe consumptionProbe = bucket.tryConsumeAndReturnRemaining(REQUEST_COST_IN_TOKENS);

        return !isRateLimitExceeded(request, response, clientIp, consumptionProbe);
    }

    private boolean isRateLimitExceeded(HttpServletRequest request, HttpServletResponse response, String clientIp, ConsumptionProbe consumptionProbe) {
        if(rateLimitService.isForbiddenClient(clientIp)) {
            log.warn("Forbidden Client Try to Access - Client IP : {}", clientIp);
            RateLimitResponse.errorResponse(response);

            return true;
        }

        // 버킷에 있는 토큰을 다 사용한 경우
        if(!consumptionProbe.isConsumed()) {
            rateLimitService.isLimitReachedThreshold(clientIp);

            float waitForRefill = (float) consumptionProbe.getNanosToWaitForRefill() / (float) Math.pow(10,9);
            RateLimitResponse.errorResponse(response, BUCKET_CAPACITY, CALLS_IN_SECONDS, waitForRefill);

            return true;
        }

        RateLimitResponse.successResponse(response, consumptionProbe.getRemainingTokens(), BUCKET_CAPACITY, CALLS_IN_SECONDS);
        return false;
    }

    private Bucket newBucket() {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(
                        BUCKET_CAPACITY, Refill.intervally(
                                BUCKET_TOKENS, CALLS_IN_SECONDS
                        )
                )).build();
    }
}
