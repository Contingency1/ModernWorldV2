package kr.modernworld.modernworldv2.user.infrastructure.auth.jwt;

public class JwtValidationCustomException extends RuntimeException {

  public JwtValidationCustomException(String message) {
    super(message);
  }

  public JwtValidationCustomException(String message, Throwable cause) {
    super(message, cause);
  }
}
