package kr.modernworld.modernworldv2.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCode {
  ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no item."),
  NO_SUCH_ALARM(HttpStatus.NOT_FOUND, "No such alarm"),
  ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "User doesn't have that alarm."),

  ACHIEVEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no achievement."),

  USER_ACHIEVEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "User doesn't have that achievement."),

  LEGEND_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no legend."),

  NO_SUCH_CHARACTER(HttpStatus.NOT_FOUND, "There is no character."),

  CHARACTER_ALREADY_EXISTS(HttpStatus.CONFLICT, "User already owns the character."),
  CHARACTER_NOT_FOUND_IN_CHARACTER_LOCKER(HttpStatus.NOT_FOUND,
      "User does not have that character."),

  ITEM_ALREADY_EXISTS_IN_INVENTORY(HttpStatus.CONFLICT, "User already owns the item."),
  ITEM_TYPE_NOT_FOUND_IN_INVENTORY(HttpStatus.NOT_FOUND, "There is no item type like that."),
  ITEM_NOT_FOUND_IN_INVENTORY(HttpStatus.NOT_FOUND, "User doesn't have that item."),

  USER_NOT_HAS_ENOUGH_POINT(HttpStatus.FORBIDDEN, "User does not have enough point."),
  USER_NOT_FOUND(HttpStatus.FORBIDDEN, "User not found."),

  PRESENT_INVALID_STATE(HttpStatus.CONFLICT, "Cannot process the present request."),
  PRESENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Present not found."),
  PRESENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Present access denied.");

  BusinessErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  private final HttpStatus status;
  private final String message;
}
