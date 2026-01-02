package kr.modernworld.modernworldv2.growth.application.achievement;

import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementInfoDTO;
import kr.modernworld.modernworldv2.growth.domain.achievement.port.AchievementQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AchievementService {

  private final AchievementQueryRepository achievementQueryRepository;

  public AchievementInfoDTO getAchievementInfo(String achievementName) {
    return achievementQueryRepository
        .findAchievementInfoByName(achievementName)
        .orElseThrow(
            () -> new BusinessException(BusinessErrorCode.ACHIEVEMENT_NOT_FOUND));
  }
}
