package kr.modernworld.modernworldv2.admin.domain.achievement.port;

import kr.modernworld.modernworldv2.admin.application.achievement.api.AchievementInfoDTO;

public interface AchievementApi {

  AchievementInfoDTO getAchievementInfo(String achievementName);
}
