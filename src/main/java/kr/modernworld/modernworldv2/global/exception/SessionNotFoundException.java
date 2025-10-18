package kr.modernworld.modernworldv2.global.exception;

public class SessionNotFoundException extends RuntimeException {

  public SessionNotFoundException(String message) {
    super(message);
  }
}
