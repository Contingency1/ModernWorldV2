package kr.modernworld.modernworldv2.growth.application.achievement;

import java.util.List;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementDTO;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementInfoDTO;
import kr.modernworld.modernworldv2.growth.application.achievement.port.AchievementQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AchievementService {

  private final AchievementQueryRepository achievementQueryRepository;

  @Transactional(readOnly = true)
  public List<AchievementDTO> getAll() {
    return achievementQueryRepository.findAll();
  }

  public AchievementInfoDTO getAchievementInfo(String achievementName) {
    return achievementQueryRepository
        .findAchievementInfoByName(achievementName)
        .orElseThrow(
            () -> new BusinessException(BusinessErrorCode.ACHIEVEMENT_NOT_FOUND));
  }
}
