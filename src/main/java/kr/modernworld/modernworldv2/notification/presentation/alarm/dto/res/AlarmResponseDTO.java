package kr.modernworld.modernworldv2.notification.presentation.alarm.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.notification.application.alarm.dto.AlarmDTO;

public record AlarmResponseDTO(
    Long no,
    Long userNo,
    String title,
    String content,
    Boolean status,
    Instant createdAt
) {

  public static AlarmResponseDTO from(AlarmDTO alarm) {
    return new AlarmResponseDTO(
        alarm.no(),
        alarm.userNo(),
        alarm.title().toString(),
        alarm.content(),
        alarm.status(),
        alarm.createdAt()
    );
  }
}
