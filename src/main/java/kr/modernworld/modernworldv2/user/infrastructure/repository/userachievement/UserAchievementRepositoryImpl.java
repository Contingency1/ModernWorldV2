package kr.modernworld.modernworldv2.user.infrastructure.repository.userachievement;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserAchievementJPAEntity.userAchievementJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.UserAchievement;
import kr.modernworld.modernworldv2.user.domain.port.userachievement.UserAchievementRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.UserAchievementMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserAchievementJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAchievementRepositoryImpl implements UserAchievementRepository {

  private final UserAchievementJPARepository userAchievementJPARepository;
  private final UserAchievementMapper userAchievementMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public UserAchievement save(UserAchievement userAchievement) {
    UserAchievementJPAEntity entity = userAchievementJPARepository.save(
        userAchievementMapper.toEntity(userAchievement));

    return userAchievementMapper.toDomain(entity);
  }

  @Override
  public Optional<UserAchievement> findByNoForUpdate(Long userAchievementNo) {
    UserAchievementJPAEntity entity = queryFactory
        .selectFrom(userAchievementJPAEntity)
        .where(userAchievementJPAEntity.no.eq(userAchievementNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(userAchievementMapper.toDomain(entity));
  }

  @Override
  public Optional<UserAchievement> findByUserNoAndStatusForUpdate(Long userNo, Boolean status) {
    UserAchievementJPAEntity entity = queryFactory
        .selectFrom(userAchievementJPAEntity)
        .where(
            userAchievementJPAEntity.user.no.eq(userNo),
            userAchievementJPAEntity.status.eq(status))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(userAchievementMapper.toDomain(entity));
  }
}
