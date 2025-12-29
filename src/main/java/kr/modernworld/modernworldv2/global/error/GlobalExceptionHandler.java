package kr.modernworld.modernworldv2.global.error;

import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // Global
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponseDTO> handleAllException(RuntimeException ex) {
    log.error(ex.getMessage(), ex);

    ErrorResponseDTO response = new ErrorResponseDTO(
        "Server Error",
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
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
        .map(error -> {
          if (error.isBindingFailure()) {
            return String.format("'%s': invalid input. (input: '%s')",
                error.getField(), error.getRejectedValue());
          } else {
            return String.format("'%s': %s", error.getField(), error.getDefaultMessage());
          }
        })
        .collect(Collectors.joining(", "));

    ErrorResponseDTO response = new ErrorResponseDTO(bindingResults,
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  // validation type mismatch.
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponseDTO> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {

    String fieldName = ex.getName();

    String message = String.format("'%s' filed type is mismatched.", fieldName);

    ErrorResponseDTO response = new ErrorResponseDTO(message,
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  // validation failed.
  @ExceptionHandler({ConstraintViolationException.class, BindException.class})
  public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(
      ConstraintViolationException ex) {

    String violations = ex.getConstraintViolations().stream().map(violation -> {
      String path = violation.getPropertyPath().toString();
      String fieldName = path.substring(path.lastIndexOf('.') + 1);

      return String.format("%s: %s", fieldName, violation.getMessage());
    }).collect(Collectors.joining(", "));

    ErrorResponseDTO response = new ErrorResponseDTO(
        violations,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value());

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  // business exception.
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex) {
    ErrorResponseDTO response = new ErrorResponseDTO(ex.getMessage(),
        ex.getErrorCode().getMessage(), ex.getErrorCode().getStatus().value());

    return new ResponseEntity<>(response, ex.getErrorCode().getStatus());
  }
}
