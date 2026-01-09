package kr.modernworld.modernworldv2.growth.infrastructure.repository.achievement;

import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity.achievementJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementDTO;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementInfoDTO;
import kr.modernworld.modernworldv2.growth.application.achievement.port.AchievementQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AchievementQueryRepositoryImpl implements AchievementQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<AchievementInfoDTO> findAchievementInfoByName(String name) {
    AchievementInfoDTO data = queryFactory
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

  @Override
  public List<AchievementDTO> findAll() {
    return queryFactory
        .select(Projections.constructor(AchievementDTO.class,
            achievementJPAEntity.no,
            achievementJPAEntity.title,
            achievementJPAEntity.description,
            achievementJPAEntity.level.stringValue(),
            achievementJPAEntity.point
        ))
        .from(achievementJPAEntity)
        .fetch();
  }
}
