package kr.modernworld.modernworldv2.user.infrastructure.repository.userachievement;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserAchievementJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAchievementJPARepository extends
    JpaRepository<UserAchievementJPAEntity, Long> {

}
