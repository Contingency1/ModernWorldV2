package kr.modernworld.modernworldv2.growth.domain.legend;

import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Legend {

  private final Long userNo;

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

  public void decrementLegend(Long userNo, LegendField legendField) {
    validationUser(userNo);

    switch (legendField) {
      case ATTENDANCE_COUNT -> decrementAttendance();
      case ITEM_COUNT -> decrementItemCount();
      case PRESENT_COUNT -> decrementPresentCount();
      case LIKE_COUNT -> decrementLikeCount();
      case COMMENT_COUNT -> decrementCommentCount();
      case RSP_WIN_COUNT -> decrementRspWinCount();
    }
  }

  private void validateDecrement(Long currentValue, String fieldName) {
    if (currentValue == null || currentValue <= 0L) {
      throw new IllegalStateException("Legend " + fieldName + " cannot be decremented below zero.");
    }
  }

  private void decrementAttendance() {
    validateDecrement(this.attendanceCount, "attendanceCount");
    this.attendanceCount--;
  }

  private void decrementItemCount() {
    validateDecrement(this.itemCount, "itemCount");
    this.itemCount--;
  }

  private void decrementPresentCount() {
    validateDecrement(this.presentCount, "presentCount");
    this.presentCount--;
  }

  private void decrementLikeCount() {
    validateDecrement(this.likeCount, "likeCount");
    this.likeCount--;
  }

  private void decrementCommentCount() {
    validateDecrement(this.commentCount, "commentCount");
    this.commentCount--;
  }

  private void decrementRspWinCount() {
    validateDecrement(this.rspWinCount, "rspWinCount");
    this.rspWinCount--;
  }

  public void incrementLegend(Long userNo, LegendField legendField) {
    validationUser(userNo);

    switch (legendField) {
      case ATTENDANCE_COUNT -> incrementAttendanceCount();
      case ITEM_COUNT -> incrementItemCount();
      case PRESENT_COUNT -> incrementPresentCount();
      case LIKE_COUNT -> incrementLikeCount();
      case COMMENT_COUNT -> incrementCommentCount();
      case RSP_WIN_COUNT -> incrementRspWinCount();
    }
  }

  private void incrementAttendanceCount() {
    this.attendanceCount++;
  }

  private void incrementItemCount() {
    this.itemCount++;
  }

  private void incrementPresentCount() {
    this.presentCount++;
  }

  private void incrementLikeCount() {
    this.likeCount++;
  }

  private void incrementCommentCount() {
    this.commentCount++;
  }

  private void incrementRspWinCount() {
    this.rspWinCount++;
  }

  private void validationUser(Long userNo) {
    if (!this.userNo.equals(userNo)) {
      throw new IllegalStateException("This Legend is not user's.");
    }
  }
}
