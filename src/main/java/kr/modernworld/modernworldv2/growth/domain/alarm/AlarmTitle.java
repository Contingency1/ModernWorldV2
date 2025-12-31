package kr.modernworld.modernworldv2.growth.domain.alarm;

import lombok.Getter;

@Getter
public enum AlarmTitle {

  NEIGHBOR("이웃"), LIKE("좋아요"), GAME("게임"),
  PRESENT("선물"), ACHIEVEMENT("업적"), COMMENT("방명록"),
  POST("쪽지"), ETC("기타");

  final String title;

  AlarmTitle(String title) {
    this.title = title;
  }

  public static AlarmTitle stringToAlarmTitle(String title) {
    for (AlarmTitle alarmTitle : AlarmTitle.values()) {
      if (alarmTitle.title.equals(title)) {
        return alarmTitle;
      }
    }

    throw new IllegalArgumentException("Invalid port title: " + title);
  }

  public String toString() {
    return title;
  }
}
