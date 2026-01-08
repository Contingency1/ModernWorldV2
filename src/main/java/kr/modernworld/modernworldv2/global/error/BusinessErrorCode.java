package kr.modernworld.modernworldv2.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessErrorCode {
  REDIS_CONCURRENT_UPDATE_REQUEST(HttpStatus.TOO_MANY_REQUESTS,
      "Token renewal is already in progress. Please wait."),
  INVALID_OAUTH_STATE(HttpStatus.UNAUTHORIZED,
      "Login failed due to invalid state parameter. (Potential CSRF attack)"),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,
      "Refresh Token is invalid or expired. Please login again."),

  CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, "Character Not Found"),
  ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no item."),
  NO_SUCH_ALARM(HttpStatus.NOT_FOUND, "No such alarm"),
  ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "User doesn't have that alarm."),

  LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no like."),
  LIKE_CANNOT_LIKE_YOURSELF(HttpStatus.FORBIDDEN, "Cannot like yourself"),
  LIKE_CANNOT_DELETE_OTHERS(HttpStatus.FORBIDDEN, "Cannot delete others"),
  LIKE_ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked"),

  COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no comment."),
  COMMENT_NOT_A_SENDER(HttpStatus.FORBIDDEN, "User is not a sender."),

  REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no reply."),
  REPLY_USER_NOT_HAVE(HttpStatus.FORBIDDEN, "This reply is not user's."),

  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no post."),
  POST_USER_NOT_HAVE(HttpStatus.FORBIDDEN, "This post is not user's"),
  POST_CANNOT_POST_TO_YOURSELF(HttpStatus.FORBIDDEN,
      "You cannot send a Post to yourself."),

  NEIGHBOR_NOT_FOUND(HttpStatus.NOT_FOUND, "There is no neighbor."),
  NEIGHBOR_ALREADY_NEIGHBOR(HttpStatus.CONFLICT, "Already neighbor."),
  NEIGHBOR_CANNOT_DELETE(HttpStatus.FORBIDDEN, "This neighbor is not user's."),
  NEIGHBOR_CANNOT_INVITE_TO_YOURSELF(HttpStatus.FORBIDDEN, "You cannot invite yourself."),
  NEIGHBOR_ALREADY_REQUESTED(HttpStatus.CONFLICT, "Already requested."),
  NEIGHBOR_ALREADY_RECEIVED(HttpStatus.CONFLICT, "Already received."),

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
  USER_NOT_HAS_ENOUGH_CHANCE(HttpStatus.FORBIDDEN, "You do not have enough chance."),
  USER_ALREADY_ATTENDANCE(HttpStatus.CONFLICT, "User already attendance."),

  PRESENT_CANNOT_PRESENT_TO_YOURSELF(HttpStatus.FORBIDDEN, "You cannot send a post to yourself."),
  PRESENT_INVALID_STATE(HttpStatus.FORBIDDEN, "Present is not a valid state."),
  PRESENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Present not found."),
  PRESENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Present access denied.");

  BusinessErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  private final HttpStatus status;
  private final String message;
}
