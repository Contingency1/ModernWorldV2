package kr.modernworld.modernworldv2.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Legend {

  private Long userNo;

  private Long attendanceCount;

  private Long itemCount;

  private Long presentCount;

  private Long likeCount;

  private Long commentCount;

  private Long rspWinCount;

  @Builder
  private Legend(Long userNo, Long attendanceCount, Long itemCount, Long presentCount,
      Long likeCount,
      Long commentCount, Long rspWinCount) {
    this.userNo = userNo;
    this.attendanceCount = attendanceCount;
    this.itemCount = itemCount;
    this.presentCount = presentCount;
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.rspWinCount = rspWinCount;
  }

  public static Legend init(Long userNo) {
    return Legend.builder()
        .userNo(userNo)
        .attendanceCount(0L)
        .itemCount(0L)
        .presentCount(0L)
        .likeCount(0L)
        .commentCount(0L)
        .rspWinCount(0L)
        .build();
  }

  public void incrementAttendanceCount(Long userNo) {
    validationUser(userNo);
    attendanceCount++;
  }

  public void incrementItemCount(Long userNo) {
    validationUser(userNo);
    itemCount++;
  }

  public void incrementPresentCount(Long userNo) {
    validationUser(userNo);
    presentCount++;
  }

  public void incrementLikeCount(Long userNo) {
    validationUser(userNo);
    likeCount++;
  }

  public void incrementCommentCount(Long userNo) {
    validationUser(userNo);
    commentCount++;
  }

  public void incrementRspWinCount(Long userNo) {
    validationUser(userNo);
    rspWinCount++;
  }

  private void validationUser(Long userNo) {
    if (!this.userNo.equals(userNo)) {
      throw new IllegalArgumentException("This Legend is not user's.");
    }
  }
}
