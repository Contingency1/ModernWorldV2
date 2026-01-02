package kr.modernworld.modernworldv2.growth.infrastructure.repository.achievement;

import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity.achievementJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementInfoDTO;
import kr.modernworld.modernworldv2.growth.domain.achievement.port.AchievementQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AchievementQueryRepositoryImpl implements AchievementQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<AchievementInfoDTO> findAchievementInfoByName(String name) {
    AchievementInfoDTO data = jpaQueryFactory
        .select(Projections.constructor(AchievementInfoDTO.class,
            achievementJPAEntity.no,
            achievementJPAEntity.point,
            achievementJPAEntity.name
        ))
        .from(achievementJPAEntity)
        .where(achievementJPAEntity.name.eq(name))
        .fetchOne();

    if (data == null) {
      return Optional.empty();
    }

    return Optional.of(data);
  }
}
