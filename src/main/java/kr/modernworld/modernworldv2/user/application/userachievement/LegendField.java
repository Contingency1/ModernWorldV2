package kr.modernworld.modernworldv2.user.application.userachievement;

import lombok.Getter;

@Getter
public enum LegendField {

  COMMENT_COUNT("commentCount"), LIKE_COUNT("likeCount"),
  ITEM_COUNT("itemCount"), PRESENT_COUNT("presentCount"),
  ATTENDANCE_COUNT("attendanceCount"), RSP_WIN_COUNT("RSPWintCount");

  private final String title;

  LegendField(String title) {
    this.title = title;
  }

}
