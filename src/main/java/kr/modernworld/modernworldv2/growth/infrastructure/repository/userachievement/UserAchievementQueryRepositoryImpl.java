package kr.modernworld.modernworldv2.growth.infrastructure.repository.userachievement;

import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QAchievementJPAEntity.achievementJPAEntity;
import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserAchievementJPAEntity.userAchievementJPAEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.modernworld.modernworldv2.growth.application.userachievement.port.UserAchievementQueryRepository;
import kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res.AchievementDetail;
import kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res.UserAchievementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAchievementQueryRepositoryImpl implements UserAchievementQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<UserAchievementResponseDTO> getUserAchievements(Long userNo, String title,
      String category) {

    return queryFactory
        .select(userAchievementSelect())
        .from(userAchievementJPAEntity)
        .join(userAchievementJPAEntity.achievement, achievementJPAEntity)
        .where(userAchievementJPAEntity.user.no.eq(userNo),
            titleContains(title),
            categoryContains(category))
        .fetch();
  }

  @Override
  public Boolean existsByUserNoAndAchievementName(Long userNo, String achievementName) {
    Integer exists = queryFactory
        .selectOne()
        .from(userAchievementJPAEntity)
        .where(userAchievementJPAEntity.user.no.eq(userNo),
            userAchievementJPAEntity.achievement.name.eq(achievementName))
        .fetchFirst();

    return exists != null;
  }

  private static ConstructorExpression<UserAchievementResponseDTO> userAchievementSelect() {
    return Projections.constructor(UserAchievementResponseDTO.class,
        userAchievementJPAEntity.no,
        userAchievementJPAEntity.user.no,
        userAchievementJPAEntity.achievement.no,
        userAchievementJPAEntity.status,
        userAchievementJPAEntity.createdAt,

        Projections.constructor(AchievementDetail.class,
            achievementJPAEntity.title,
            achievementJPAEntity.description,
            achievementJPAEntity.level.stringValue(),
            achievementJPAEntity.category
        )
    );
  }

  private static BooleanExpression categoryContains(String category) {
    if (category == null || category.isEmpty()) {
      return null;
    }

    return userAchievementJPAEntity.achievement.category.contains(category);
  }

  private static BooleanExpression titleContains(String title) {
    if (title == null || title.isEmpty()) {
      return null;
    }

    return userAchievementJPAEntity.achievement.title.contains(title);
  }
}
