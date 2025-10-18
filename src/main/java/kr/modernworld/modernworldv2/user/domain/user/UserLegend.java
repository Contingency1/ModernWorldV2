package kr.modernworld.modernworldv2.user.domain.user;

import lombok.Getter;

@Getter
public class UserLegend {

  private final Long userNo;

  private Long attendanceCount;
  private Long itemCount;
  private Long presentCount;
  private Long likeCount;
  private Long commentCount;
  private Long rspWinCount;

  public void incrementAttendanceCount() {
    attendanceCount++;
  }

  public void incrementItemCount() {
    itemCount++;
  }

  public void incrementPresentCount() {
    presentCount++;
  }

  public void incrementLikeCount() {
    likeCount++;
  }

  public void incrementCommentCount() {
    commentCount++;
  }

  public void incrementRspWinCount() {
    rspWinCount++;
  }

  public UserLegend(Long userNo, Long attendanceCount, Long itemCount, Long presentCount,
      Long likeCount, Long commentCount, Long rspWinCount) {
    this.userNo = userNo;
    this.attendanceCount = attendanceCount;
    this.itemCount = itemCount;
    this.presentCount = presentCount;
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.rspWinCount = rspWinCount;
  }

  public static UserLegend init(Long userNo) {
    return new UserLegend(userNo, 0L, 0L, 0L, 0L, 0L, 0L);
  }

  public static UserLegend get(Long userNo, Long attendanceCount, Long itemCount,
      Long presentCount, Long likeCount, Long commentCount, Long rspWinCount) {
    return new UserLegend(userNo, attendanceCount, itemCount,
        presentCount, likeCount,
        commentCount, rspWinCount);
  }
}
