package kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res;

import kr.modernworld.modernworldv2.growth.application.userachievement.dto.GetAchievementDTO;

public record AchievementDetail(
    String title,
    String description,
    String level,
    String category
) {

  public static AchievementDetail from(GetAchievementDTO achievementDetail) {
    return new AchievementDetail(
        achievementDetail.title(),
        achievementDetail.description(),
        achievementDetail.level(),
        achievementDetail.category()
    );
  }
}
