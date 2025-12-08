package kr.modernworld.modernworldv2.admin.domain.port.achievement;

import java.util.Optional;
import kr.modernworld.modernworldv2.admin.application.api.AchievementInfoDTO;

public interface AchievementQueryRepository {

  Optional<AchievementInfoDTO> findAchievementInfoByName(String name);

}
