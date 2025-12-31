package kr.modernworld.modernworldv2.social.domain.neighbor;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Neighbor {

  private final Long no;

  private final Long senderNo;

  private final Long receiverNo;

  private Boolean status;

  private final Instant createdAt;

  @Builder
  private Neighbor(Long no, Long senderNo, Long receiverNo, Boolean status, Instant createdAt) {
    this.no = no;
    this.senderNo = senderNo;
    this.receiverNo = receiverNo;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static Neighbor init(Long senderNo, Long receiverNo) {
    if (senderNo.equals(receiverNo)) {
      throw new IllegalArgumentException("senderNo and receiverNo cannot be the same");
    }

    return Neighbor.builder()
        .senderNo(senderNo)
        .receiverNo(receiverNo)
        .status(false)
        .createdAt(Instant.now())
        .build();
  }

  public void makeStatusTrue() {
    if (status) {
      throw new IllegalStateException("This neighbor is already received");
    }

    this.status = true;
  }

}
