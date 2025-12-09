package kr.modernworld.modernworldv2.user.domain.post;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

  private final Long no;

  private final Long senderNo;

  private final Long receiverNo;

  private final String content;

  private final Instant createdAt;

  private Boolean check;

  private Boolean senderDelete;

  private Boolean receiverDelete;

  public void makeCheckTrue(Long userNo) {
    validateUser(userNo, receiverNo);

    check = true;
  }

  public void deleteFromSender(Long userNo) {
    validateUser(userNo, senderNo);

    this.senderDelete = true;
  }

  public void deleteFromReceiver(Long userNo) {
    validateUser(userNo, receiverNo);

    this.receiverDelete = true;
  }

  private void validateUser(Long userNo, Long targetUserNo) {
    if (!userNo.equals(targetUserNo)) {
      throw new IllegalStateException("User does not belong to this post");
    }
  }

  @Builder
  private Post(Long no, Long senderNo, Long receiverNo, String content, Instant createdAt,
      Boolean check, Boolean senderDelete, Boolean receiverDelete) {
    this.no = no;
    this.senderNo = senderNo;
    this.receiverNo = receiverNo;
    this.content = content;
    this.createdAt = createdAt;
    this.check = check;
    this.senderDelete = senderDelete;
    this.receiverDelete = receiverDelete;
  }

  public static Post init(Long senderNo, Long receiverNo, String content) {
    if (senderNo.equals(receiverNo)) {
      throw new IllegalArgumentException(
          "Cannot create a new post with the same sender, receiver no");
    }

    return Post.builder()
        .senderNo(senderNo)
        .receiverNo(receiverNo)
        .content(content)
        .createdAt(Instant.now())
        .check(false)
        .senderDelete(false)
        .receiverDelete(false)
        .build();
  }
}
