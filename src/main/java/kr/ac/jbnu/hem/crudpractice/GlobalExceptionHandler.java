
package kr.ac.jbnu.hem.crudpractice;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 잘못된 JSON 또는 읽을 수 없는 요청 본문 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(
            HttpMessageNotReadableException ex) {

        return ResponseEntity.status(400).body(
                Map.of(
                        "status", "error",
                        "data", "요청 JSON 형식이 올바르지 않습니다."
                )
        );
    }

    // 그 외 예상하지 못한 서버 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(
            Exception ex) {

        return ResponseEntity.status(500).body(
                Map.of(
                        "status", "error",
                        "data", "서버 내부 오류가 발생했습니다."
                )
        );
    }
}
