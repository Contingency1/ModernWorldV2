package kr.modernworld.modernworldv2.user.domain.userachievement.port;

import java.util.List;
import kr.modernworld.modernworldv2.user.presentation.userachievement.dto.res.UserAchievementResponseDTO;

public interface UserAchievementQueryRepository {

  List<UserAchievementResponseDTO> getUserAchievements(Long userNo, String title,
      String category);

  Boolean existsByUserNoAndAchievementName(Long userNo, String achievementName);
}
