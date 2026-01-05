package kr.modernworld.modernworldv2.asset.domain.item;

import lombok.Getter;

@Getter
public enum ItemType {
  ONE("1번 타입"), TWO("2번 타입"), THREE("3번 타입"), FOUR("4번 타입"),
  FIVE("5번 타입"), SIX("6번 타입"), SEVEN("7번 타입"), EIGHT("8번 타입"),
  NINE("9번 타입"), TEN("10번 타입"), ELEVEN("11번 타입"), TWELVE("12번 타입");

  private final String str;

  ItemType(String str) {
    this.str = str;
  }

  public static ItemType stringToItemType(String str) {
    for (ItemType itemType : ItemType.values()) {
      if (itemType.str.equals(str)) {
        return itemType;

      }
    }
    throw new IllegalArgumentException(str);
  }
}
