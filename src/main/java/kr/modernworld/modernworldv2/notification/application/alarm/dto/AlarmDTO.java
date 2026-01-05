package kr.modernworld.modernworldv2.notification.application.alarm.dto;

import java.time.Instant;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;

public record AlarmDTO(
    Long no,
    Long userNo,
    AlarmTitle title,
    String content,
    Boolean status,
    Instant createdAt
) {

}
