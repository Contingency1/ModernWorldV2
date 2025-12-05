package kr.modernworld.modernworldv2.user.domain.alarm;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Alarm {

  private Long no;

  private Long userNo;

  private AlarmTitle title;

  private String content;

  private Boolean status;

  private Instant createdAt;

  public void makeStatusTrue() {
    if (!status) {
      status = true;
    }
  }

  @Builder
  private Alarm(Long no, Long userNo, AlarmTitle title, String content, Boolean status,
      Instant createdAt) {
    this.no = no;
    this.userNo = userNo;
    this.title = title;
    this.content = content;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static Alarm init(Long userNo, AlarmTitle title, String content) {
    return Alarm.builder()
        .userNo(userNo)
        .title(title)
        .content(content)
        .status(false)
        .createdAt(Instant.now())
        .build();
  }

  public void validationUser(Long userNo) {
    if (!this.userNo.equals(userNo)) {
      throw new IllegalStateException("This alarm is not user's.");
    }
  }

}
