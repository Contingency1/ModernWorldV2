package kr.modernworld.modernworldv2.social.domain.reply;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Reply {

  private final Long no;

  private final Long commentNo;

  private final Long userNo;

  private String content;

  private final Instant createdAt;

  private Instant deletedAt;

  @Builder
  private Reply(Long no, Long commentNo, Long userNo, Instant createdAt, Instant deletedAt,
      String content) {
    this.no = no;
    this.commentNo = commentNo;
    this.userNo = userNo;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.content = content;
  }

  public static Reply init(Long commentNo, Long userNo, String content) {
    return Reply.builder()
        .commentNo(commentNo)
        .userNo(userNo)
        .content(content)
        .createdAt(Instant.now())
        .build();
  }

  public void updateContent(Long userNo, String content) {
    validateUserReply(userNo);

    this.content = content;
  }

  public void updateDeletedAt(Long userNo) {
    validateUserReply(userNo);

    this.deletedAt = Instant.now();
  }

  private void validateUserReply(Long userNo) {
    if (!userNo.equals(this.userNo)) {
      throw new IllegalStateException("This reply is not user's.");
    }
  }
}
