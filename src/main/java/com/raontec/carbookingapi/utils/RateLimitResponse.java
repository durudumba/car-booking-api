package com.raontec.carbookingapi.utils;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.time.Duration;

public class RateLimitResponse {

    public static void successResponse(HttpServletResponse response, long remainTokens, Long bucketCapacity, Duration callsInSeconds) {
//        response.setHeader("X-RateLimit-Remaining", Long.toString(remainTokens));
//        response.setHeader("X-RateLimit-Limit", bucketCapacity + ";w=" + callsInSeconds.getSeconds());
    }

    public static void errorResponse(HttpServletResponse response, Long bucketCapacity, Duration callsInSeconds, float waitForRefill) {
//        response.setHeader("X-RateLimit-RetryAfter", Float.toString(waitForRefill));
//        response.setHeader("X-RateLimit-Limit", bucketCapacity + ";w=" + callsInSeconds.getSeconds());

        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
    }

    public static void errorResponse(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
