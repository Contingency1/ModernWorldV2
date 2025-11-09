package kr.modernworld.modernworldv2.global.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UnhandledErrorController implements ErrorController {

  // Unhandled Error는 모두 이곳에 모임
  @RequestMapping("/error")
  public ResponseEntity<ErrorResponseDTO> error(HttpServletRequest request) {

    // 포워딩 된 Request 에서 Status 추출
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    int statusCode = (status != null) ? Integer.parseInt(status.toString()) : 500;

    // 포워딩 된 Request 에서 Exception 추출
    Throwable exception = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

    log.error("Error occurred with status {}: {}", statusCode,
        (exception != null ? exception.getMessage() : "N/A"), exception);

    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
        "Unhandled Error occurred.",
        HttpStatus.valueOf(statusCode).getReasonPhrase(),
        statusCode
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
  }
}
