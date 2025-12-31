package kr.modernworld.modernworldv2.growth.domain.achievement.port;

import kr.modernworld.modernworldv2.growth.application.achievement.api.AchievementInfoDTO;

public interface AchievementApi {

  AchievementInfoDTO getAchievementInfo(String achievementName);
}
