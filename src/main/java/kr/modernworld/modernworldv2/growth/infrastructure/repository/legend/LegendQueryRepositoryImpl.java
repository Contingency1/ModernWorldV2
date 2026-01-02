package kr.modernworld.modernworldv2.growth.infrastructure.repository.legend;

import static kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.QLegendJPAEntity.legendJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.growth.application.legend.port.LegendQueryRepository;
import kr.modernworld.modernworldv2.growth.domain.legend.Legend;
import kr.modernworld.modernworldv2.growth.infrastructure.mapper.LegendMapper;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.LegendJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LegendQueryRepositoryImpl implements LegendQueryRepository {

  private final LegendMapper legendMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<Legend> findLegendByUserNo(Long userNo) {
    LegendJPAEntity entity = queryFactory.select(legendJPAEntity)
        .from(legendJPAEntity)
        .where(legendJPAEntity.no.eq(userNo))
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(legendMapper.toDomain(entity));
  }
}
