package kr.modernworld.modernworldv2.user.domain.comment;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

  private final Long no;

  private final Long senderNo;

  private final Long receiverNo;

  private String content;

  private final Instant createdAt;

  private Instant deletedAt;

  @Builder
  private Comment(Long no, Long senderNo, Long receiverNo, String content, Instant createdAt,
      Instant deletedAt
  ) {
    this.no = no;
    this.senderNo = senderNo;
    this.receiverNo = receiverNo;
    this.content = content;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
  }

  public static Comment init(Long senderNo, Long receiverNo, String content) {
    return Comment.builder()
        .senderNo(senderNo)
        .receiverNo(receiverNo)
        .content(content)
        .createdAt(Instant.now())
        .build();
  }

  public void updateContent(Long userNo, String content) {
    validationUser(userNo);
    this.content = content;
  }

  public void updateDeletedAt(Long userNo) {
    validationUser(userNo);
    this.deletedAt = Instant.now();
  }

  private void validationUser(Long userNo) {
    if (!userNo.equals(this.senderNo)) {
      throw new IllegalStateException("User is not a sender.");
    }
  }
}
