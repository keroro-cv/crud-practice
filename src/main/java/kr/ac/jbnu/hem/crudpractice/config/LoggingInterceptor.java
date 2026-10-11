
package kr.ac.jbnu.hem.crudpractice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    // 컨트롤러 실행 전에 호출
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        System.out.println(
                "[REQUEST] " + request.getMethod()
                        + " " + request.getRequestURI()
        );

        return true;
    }

    // 요청 처리가 완료된 후 호출
    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        System.out.println(
                "[RESPONSE] " + request.getMethod()
                        + " " + request.getRequestURI()
                        + " - " + response.getStatus()
        );
    }
}
