package kr.modernworld.modernworldv2.asset.domain.present;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PresentStatus {
  UNREAD("unread"), READ("read"), ACCEPT("accept"), REJECT("reject");

  final String str;

  PresentStatus(String presentStatus) {
    this.str = presentStatus;
  }

  @JsonCreator
  public static PresentStatus stringToPresentStatus(String str) {
    for (PresentStatus status : PresentStatus.values()) {
      if (status.str.equals(str)) {
        return status;
      }
    }

    throw new IllegalArgumentException(str);
  }

  @JsonValue
  public String toString() {
    return this.str;
  }
}
