package kr.modernworld.modernworldv2.user.domain.port.userachievement;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.UserAchievement;

public interface UserAchievementRepository {

  UserAchievement save(UserAchievement userAchievement);

  Optional<UserAchievement> findOneForUpdate(Long userNo, Long userAchievementNo);

  Optional<UserAchievement> findByUserNoAndStatusForUpdate(Long userNo, Boolean status);

}
