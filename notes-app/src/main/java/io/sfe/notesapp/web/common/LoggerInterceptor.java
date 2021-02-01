package io.sfe.notesapp.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static java.util.Collections.emptyList;

public class LoggerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) {
        logger.info(
            "[preHandle][{}] {} {}?{}",
            request,
            request.getMethod(),
            request.getRequestURI(),
            extractRequestParameterNames(request.getParameterNames())
        );

        return true;
    }

    private List<String> extractRequestParameterNames(Enumeration<String> parameterNames) {
        if (parameterNames == null) {
            return emptyList();
        }

        ArrayList<String> requestParameterNames = new ArrayList<>();
        parameterNames.asIterator()
            .forEachRemaining(requestParameterNames::add);

        return requestParameterNames;
    }

    @Override
    public void postHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        ModelAndView modelAndView
    ) {
        logger.info("[postHandle][{}]", request);
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        Exception e
    ) {
        if (e != null) {
            logger.error(e.getMessage(), e);
        }
        logger.info("[afterCompletion][{}][exception: {}]", request, e);
    }
}
