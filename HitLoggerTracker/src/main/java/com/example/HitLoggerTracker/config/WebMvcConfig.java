package com.example.HitLoggerTracker.config;

import com.example.HitLoggerTracker.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**") //해당 경로에 접근하기 전에 인터셉터가 가로챈다.
                .excludePathPatterns(
                        "/admin/**",
                        "/h2-console",
                        "/error",
                        "/error/**",
                        "/css/**","/js/**","/images/**","/favicon.ico"
                ); //해당 경로는 인터셉터가 가로채지 않는다.
    }
}
