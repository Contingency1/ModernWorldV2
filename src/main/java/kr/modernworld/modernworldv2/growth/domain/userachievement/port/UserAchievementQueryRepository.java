package kr.modernworld.modernworldv2.growth.domain.userachievement.port;

import java.util.List;
import kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res.UserAchievementResponseDTO;

public interface UserAchievementQueryRepository {

  List<UserAchievementResponseDTO> getUserAchievements(Long userNo, String title,
      String category);

  Boolean existsByUserNoAndAchievementName(Long userNo, String achievementName);
}
