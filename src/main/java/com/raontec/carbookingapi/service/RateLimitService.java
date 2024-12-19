package com.raontec.carbookingapi.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitService {
    private Map<String, RequestInfo> rateLimitErrorCounter = new ConcurrentHashMap<>();
    // API 호출 횟수 초과
    public void isLimitReachedThreshold(String clientIp) {
        RequestInfo requestInfo = rateLimitErrorCounter.computeIfAbsent(clientIp,
                key -> new RequestInfo());

        requestInfo.addCount();

        if(requestInfo.isOverCount() && requestInfo.isLastRequestOverTime()) {
            requestInfo.setCount(1);
        }

        log.warn("Request Rate Limit Exceed({}) - Client IP : {}", requestInfo.getCount(), clientIp);
    }

    // 일시 접속 제한된 클라이언트 여부
    public boolean isForbiddenClient(String clientIp) {
        RequestInfo requestInfo = rateLimitErrorCounter.computeIfAbsent(clientIp,
                key -> new RequestInfo());

        return requestInfo.isOverCount() && !requestInfo.isLastRequestOverTime();
    }

    @Getter
    @Setter
    private static class RequestInfo {
        private int count = 0;
        private LocalDateTime lastRequestTime = LocalDateTime.now();

        public boolean isLastRequestOverTime() {
            LocalDateTime now = LocalDateTime.now();
            if(lastRequestTime == null || ChronoUnit.HOURS.between(lastRequestTime, now) >= 1) {
                lastRequestTime = now;
                return true;
            }
            return false;
        }

        public boolean isOverCount() {
            return count >= 10;
        }

        public void addCount() {
            this.count += 1;
        }
    }
}
