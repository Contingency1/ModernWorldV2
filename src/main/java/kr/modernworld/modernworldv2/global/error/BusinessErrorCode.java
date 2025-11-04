package kr.modernworld.modernworldv2.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCode {
  NO_SUCH_ITEM(HttpStatus.NOT_FOUND, "There is no item."),

  ITEM_ALREADY_EXISTS_IN_INVENTORY(HttpStatus.CONFLICT, "User already owns the item."),
  ITEM_TYPE_NOT_FOUND_IN_INVENTORY(HttpStatus.NOT_FOUND, "There is no item type like that."),
  ITEM_NOT_FOUND_IN_INVENTORY(HttpStatus.NOT_FOUND, "User doesn't have that item."),

  USER_NOT_HAS_ENOUGH_POINT(HttpStatus.FORBIDDEN, "User does not have enough point."),
  USER_NOT_FOUND(HttpStatus.FORBIDDEN, "User not found.");

  BusinessErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  private final HttpStatus status;
  private final String message;
}
