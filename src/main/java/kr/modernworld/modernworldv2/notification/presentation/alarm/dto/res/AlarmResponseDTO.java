package kr.modernworld.modernworldv2.notification.presentation.alarm.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.notification.domain.alarm.Alarm;

public record AlarmResponseDTO(
    Long no,
    Long userNo,
    String title,
    String content,
    Boolean status,
    Instant createdAt
) {

  public static AlarmResponseDTO from(Alarm alarm) {
    return new AlarmResponseDTO(
        alarm.getNo(),
        alarm.getUserNo(),
        alarm.getTitle().toString(),
        alarm.getContent(),
        alarm.getStatus(),
        alarm.getCreatedAt()
    );
  }

}
