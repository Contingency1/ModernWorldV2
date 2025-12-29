package kr.modernworld.modernworldv2.global.error;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final BusinessErrorCode errorCode;

  public BusinessException(BusinessErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public BusinessException(BusinessErrorCode errorCode, String plusMessage) {
    super(errorCode.getMessage() + plusMessage);
    this.errorCode = errorCode;
  }
}
