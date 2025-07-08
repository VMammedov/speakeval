package org.project.speakeval.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.project.speakeval.constants.Constants;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class LoggingUtil {

    private static final String SEPARATOR = " - ";

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(Constants.TRACE_ID_NAME, traceId);

        StringBuilder stringBuilder = new StringBuilder("Spring request: ");

        stringBuilder.append(httpServletRequest.getMethod()).append(" ");
        stringBuilder.append(httpServletRequest.getRequestURL());

        Map<String, String> parameters = buildParametersMap(httpServletRequest);
        if (!parameters.isEmpty()) {
            stringBuilder.append(SEPARATOR).append("Parameters: ").append(parameters);
        }

        if (body != null) {
            String json = new String((byte[]) body);
            stringBuilder.append(SEPARATOR).append("Body: ").append(json);
        }

        Map<String, String> headers = buildHeadersMap(httpServletRequest);
        if (headers.isEmpty()) {
            stringBuilder.append(SEPARATOR).append("Headers: {EMPTY}");
        } else {
            stringBuilder.append(SEPARATOR).append("Headers: ").append(headers);
        }

        log.info(stringBuilder.toString());
    }

    public void logResponse(HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder("Spring response: HTTP ")
                .append(httpServletResponse.getStatus());
        if (Objects.nonNull(body)) {
            stringBuilder.append(SEPARATOR).append("Body: ").append(body);
        } else {
            stringBuilder.append(SEPARATOR).append("Body: {EMPTY}");
        }

        Map<String, String> headers = buildHeadersMap(httpServletResponse);
        if (headers.isEmpty()) {
            stringBuilder.append(SEPARATOR).append("Headers: {EMPTY}");
        } else {
            stringBuilder.append(SEPARATOR).append("Headers: ").append(headers);
        }

        log.info(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            String headerValue = response.getHeader(header);
            map.put(header, headerValue);
        }

        return map;
    }
}
