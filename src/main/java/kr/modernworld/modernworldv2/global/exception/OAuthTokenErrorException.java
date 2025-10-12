package kr.modernworld.modernworldv2.global.exception;

public class OAuthTokenErrorException extends RuntimeException {

  public OAuthTokenErrorException(String message) {
    super(message);
  }
}
