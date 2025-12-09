package kr.modernworld.modernworldv2.global.common;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OrderBy {
  ASC("asc"), DESC("desc");

  final String str;

  OrderBy(String str) {
    this.str = str;
  }

  @JsonCreator
  public static OrderBy stringToEnum(String str) {
    for (OrderBy o : OrderBy.values()) {
      if (o.str.equals(str)) {
        return o;
      }
    }

    throw new IllegalArgumentException(str);
  }

  public String toString() {
    return this.str;
  }
}
