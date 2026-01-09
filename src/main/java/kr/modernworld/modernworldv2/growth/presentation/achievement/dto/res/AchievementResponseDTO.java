package kr.modernworld.modernworldv2.growth.presentation.achievement.dto.res;

import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementDTO;

public record AchievementResponseDTO(
    Long no,
    String title,
    String description,
    String level,
    Long point
) {

  public static AchievementResponseDTO from(AchievementDTO dto) {
    return new AchievementResponseDTO(dto.no(),
        dto.title(),
        dto.description(),
        dto.level(),
        dto.point());
  }

}
