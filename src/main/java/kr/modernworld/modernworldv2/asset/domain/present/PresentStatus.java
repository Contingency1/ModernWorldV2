package kr.modernworld.modernworldv2.asset.domain.present;

public enum PresentStatus {
  UNREAD("unread"), READ("read"), ACCEPT("accept"), REJECT("reject");

  final String str;

  PresentStatus(String presentStatus) {
    this.str = presentStatus;
  }

  public static PresentStatus stringToPresentStatus(String str) {
    for (PresentStatus status : PresentStatus.values()) {
      if (status.str.equals(str)) {
        return status;
      }
    }

    throw new IllegalArgumentException(str);
  }

  public String toString() {
    return this.str;
  }
}
