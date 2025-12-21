package kr.modernworld.modernworldv2.user.domain.like;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Like {

  private final Long no;

  private final Long senderNo;

  private final Long receiverNo;

  @Builder
  private Like(Long no, Long receiverNo, Long senderNo) {
    this.no = no;
    this.senderNo = senderNo;
    this.receiverNo = receiverNo;
  }

  public static Like init(Long senderNo, Long receiverNo) {
    if (senderNo.equals(receiverNo)) {
      throw new IllegalArgumentException("senderNo and receiverNo are equal");
    }

    return Like.builder()
        .senderNo(senderNo)
        .receiverNo(receiverNo).build();
  }

  public void validateDeletion(Long userNo) {
    if (!userNo.equals(this.senderNo)) {
      throw new IllegalStateException("Only Senders can delete their likes.");
    }
  }
}
