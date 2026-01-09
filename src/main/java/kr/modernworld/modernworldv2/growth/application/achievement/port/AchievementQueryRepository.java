package kr.modernworld.modernworldv2.growth.application.achievement.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementDTO;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementInfoDTO;

public interface AchievementQueryRepository {

  Optional<AchievementInfoDTO> findAchievementInfoByName(String name);

  List<AchievementDTO> findAll();

}
