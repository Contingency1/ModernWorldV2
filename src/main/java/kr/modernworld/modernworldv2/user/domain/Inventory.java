package kr.modernworld.modernworldv2.user.domain;

import java.time.Instant;
import kr.modernworld.modernworldv2.admin.domain.ItemType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Inventory {

  private final Long no;
  private final Long userNo;
  private final Long itemNo;
  private Boolean status;
  private final ItemType itemType;
  private final Instant createdAt;

  @Builder
  private Inventory(Long no, Long userNo, Long itemNo, Boolean status, ItemType itemType,
      Instant createdAt) {
    this.no = no;
    this.userNo = userNo;
    this.itemNo = itemNo;
    this.status = status;
    this.itemType = itemType;
    this.createdAt = createdAt;
  }

  public static Inventory create(Long userNo, Long itemNo) {
    return Inventory.builder()
        .userNo(userNo)
        .itemNo(itemNo)
        .itemType(null)
        .status(false)
        .createdAt(Instant.now())
        .build();
  }

  public void makeStatusTrue() {
    this.status = true;
  }

  public void makeStatusFalse() {
    this.status = false;
  }

}
