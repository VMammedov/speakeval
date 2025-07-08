package org.project.speakeval.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.project.speakeval.util.LoggingUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

public class LogInterceptor implements HandlerInterceptor {

    private final LoggingUtil loggingUtil;
    private static final List<Integer> withoutBodyList = Arrays.asList(0, -1);

    public LogInterceptor(LoggingUtil loggingUtil) {
        this.loggingUtil = loggingUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (withoutBodyList.contains(request.getContentLength())) {
            loggingUtil.logRequest(request, null);
        }
        return true;
    }
}
