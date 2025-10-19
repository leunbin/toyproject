package com.example.HitLoggerTracker.interceptor;

import com.example.HitLoggerTracker.entity.Hit;
import com.example.HitLoggerTracker.repository.HitRepository;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final HitRepository hitRepository;

    //컨트롤러 진입하기 전 실행
    //반환 값이 true일 경우 컨트롤러로 진입
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler  //진입하려는 컨트롤러의 클래스 객체가 담김
    ) throws Exception {
        logger.info("[LoggerTracker] preHandle");
        if (request.getDispatcherType() != DispatcherType.REQUEST) {
            return true;
        }
        String uri = request.getRequestURI();
        if (uri.equals("/error") || uri.startsWith("/error/")) {
            return true;
        }
        Hit hit = Hit.builder()
                .path(request.getRequestURI())
                .method(request.getMethod())
                .ip(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .at(LocalDateTime.now())
                .build();

        hitRepository.save(hit);
        return true;
    }

    //컨트롤러 진입 후  View가 랜더링 되기 전에 수행
    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView
    ) throws Exception {
        logger.info("[LoggerTracker] postHandle");
    }

    //컨트롤러 진입 후 view가 랜더링 된 후에 실행
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        logger.info(("[LoggerTracker] afterCompletion"));
    }
}
