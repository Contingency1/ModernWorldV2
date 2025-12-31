package kr.modernworld.modernworldv2.growth.domain.achievement.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.growth.application.achievement.api.AchievementInfoDTO;

public interface AchievementQueryRepository {

  Optional<AchievementInfoDTO> findAchievementInfoByName(String name);

}
