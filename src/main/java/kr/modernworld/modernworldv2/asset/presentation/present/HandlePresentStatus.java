package kr.modernworld.modernworldv2.asset.presentation.present;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HandlePresentStatus {
  ACCEPT("accept"),
  REJECT("reject");

  final String str;

  HandlePresentStatus(String str) {
    this.str = str;
  }

  @JsonCreator
  public static HandlePresentStatus fromString(String str) {
    for (HandlePresentStatus action : HandlePresentStatus.values()) {
      if (action.str.equals(str)) {
        return action;
      }
    }

    throw new IllegalArgumentException(str);
  }

  @JsonValue
  public String toString() {
    return this.str;
  }
}
