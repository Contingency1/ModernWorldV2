package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserBatchRepositoryImpl implements UserBatchRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Long deleteExpiredUsers(LocalDateTime threshold) {
    return queryFactory
        .delete(userJPAEntity)
        .where(userJPAEntity.deletedAt.loe(Instant.from(threshold)))
        .execute();
  }

  @Override
  public Long resetAllUserChance(Long count) {
    return queryFactory
        .update(userJPAEntity)
        .set(userJPAEntity.chance, count)
        .where(userJPAEntity.deletedAt.isNull())
        .execute();
  }

  @Override
  public Long resetAllUserAttendance(Map<String, List<Integer>> data) {
    return queryFactory
        .update(userJPAEntity)
        .set(userJPAEntity.attendance, data)
        .where(userJPAEntity.deletedAt.isNull())
        .execute();
  }
}
