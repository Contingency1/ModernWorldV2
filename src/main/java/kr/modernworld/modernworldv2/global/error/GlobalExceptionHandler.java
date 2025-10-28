package kr.modernworld.modernworldv2.global.error;

import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Global
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponseDTO> handleAllException(RuntimeException ex) {
    log.error(ex.getMessage(), ex);

    ErrorResponseDTO response = new ErrorResponseDTO(
        "Server Error",
        "INTERNAL_SERVER_ERROR",
        HttpStatus.INTERNAL_SERVER_ERROR.value()
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

  // Validation Error.
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDTO> handleValidationFailed(
      MethodArgumentNotValidException ex) {

    String bindingResults = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error ->
            String.format("%s: %s", error.getField(), error.getDefaultMessage()))
        .collect(Collectors.joining(","));

    ErrorResponseDTO response = new ErrorResponseDTO(bindingResults,
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
