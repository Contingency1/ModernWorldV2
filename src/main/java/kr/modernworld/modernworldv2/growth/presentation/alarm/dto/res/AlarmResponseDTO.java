package kr.modernworld.modernworldv2.growth.presentation.alarm.dto.res;

import java.time.Instant;

public record AlarmResponseDTO(
    Long no,
    Long userNo,
    String title,
    String content,
    Boolean status,
    Instant createdAt
) {

}
