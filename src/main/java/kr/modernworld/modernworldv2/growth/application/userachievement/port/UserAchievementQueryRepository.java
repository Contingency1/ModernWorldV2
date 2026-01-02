package kr.modernworld.modernworldv2.growth.application.userachievement.port;

import java.util.List;
import kr.modernworld.modernworldv2.growth.application.userachievement.dto.GetUserAchievementDTO;

public interface UserAchievementQueryRepository {

  List<GetUserAchievementDTO> getUserAchievements(Long userNo, String title,
      String category);

  Boolean existsByUserNoAndAchievementName(Long userNo, String achievementName);
}
