package kr.modernworld.modernworldv2.admin.application.api;

import kr.modernworld.modernworldv2.admin.domain.port.achievement.AchievementQueryRepository;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
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
