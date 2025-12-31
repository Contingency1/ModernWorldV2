package kr.modernworld.modernworldv2.growth.application.achievement.api;

import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.domain.achievement.port.AchievementApi;
import kr.modernworld.modernworldv2.growth.domain.achievement.port.AchievementQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AchievementApiImpl implements AchievementApi {

  private final AchievementQueryRepository achievementQueryRepository;

  @Override
  public AchievementInfoDTO getAchievementInfo(String achievementName) {
    return achievementQueryRepository
        .findAchievementInfoByName(achievementName)
        .orElseThrow(
            () -> new BusinessException(BusinessErrorCode.ACHIEVEMENT_NOT_FOUND));
  }
}
