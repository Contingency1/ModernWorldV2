package kr.modernworld.modernworldv2.user.infrastructure.repository.legend;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QLegendJPAEntity.legendJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.legend.Legend;
import kr.modernworld.modernworldv2.user.domain.legend.port.LegendRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.LegendMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LegendJPAEntity;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LegendRepositoryImpl implements LegendRepository {

  private final LegendJPARepository legendJPARepository;
  private final LegendMapper legendMapper;
  private final EntityManager entityManager;
  private final JPAQueryFactory queryFactory;

  @Override
  public Legend save(Legend legend) {
    Optional<LegendJPAEntity> entity = legendJPARepository.findById(legend.getUserNo());

    if (entity.isPresent()) {
      LegendJPAEntity existingEntity = entity.get();

      legendMapper.updateEntityFromDomain(legend, existingEntity);

      return legendMapper.toDomain(legendJPARepository.save(existingEntity));
    }

    LegendJPAEntity newEntity = legendMapper.toEntity(legend);

    newEntity.setUser(entityManager.getReference(UserJPAEntity.class, legend.getUserNo()));

    return legendMapper.toDomain(legendJPARepository.save(newEntity));
  }

  @Override
  public Optional<Legend> findByUserNoForUpdate(Long userNo) {
    LegendJPAEntity entity = queryFactory
        .select(legendJPAEntity)
        .from(legendJPAEntity)
        .where(legendJPAEntity.user.no.eq(userNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(legendMapper.toDomain(entity));
  }
}
