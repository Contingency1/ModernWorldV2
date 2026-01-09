package kr.modernworld.modernworldv2.member.application.user;

import lombok.Getter;

@Getter
public enum OrderByField {
  LIKE("like"), ACCUMULATION_POINT("accumulationPoint"), CREATED_AT("createdAt");

  final String field;

  OrderByField(String field) {
    this.field = field;
  }

  public static OrderByField stringToOrderByField(String field) {
    for (OrderByField o : OrderByField.values()) {
      if (o.field.equals(field)) {
        return o;
      }
    }

    return OrderByField.CREATED_AT;
  }
}
