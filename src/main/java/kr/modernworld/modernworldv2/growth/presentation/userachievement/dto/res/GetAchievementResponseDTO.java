package kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res;

import kr.modernworld.modernworldv2.growth.application.userachievement.dto.GetAchievementDTO;

public record GetAchievementResponseDTO(
    String title,
    String description,
    String level,
    String category
) {

  public static GetAchievementResponseDTO from(GetAchievementDTO achievementDetail) {
    return new GetAchievementResponseDTO(
        achievementDetail.title(),
        achievementDetail.description(),
        achievementDetail.level(),
        achievementDetail.category()
    );
  }
}
