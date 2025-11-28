package kr.modernworld.modernworldv2.user.infrastructure.repository.legend;

import jakarta.persistence.EntityManager;
import kr.modernworld.modernworldv2.user.domain.Legend;
import kr.modernworld.modernworldv2.user.domain.port.legend.LegendRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.LegendMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LegendJPAEntity;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LegendRepositoryImpl implements LegendRepository {

  private final LegendJPARepository legendJPARepository;
  private final LegendMapper legendMapper;
  private final EntityManager entityManager;

  @Override
  public void save(Legend legend) {
    LegendJPAEntity entity = legendMapper.toEntity(legend);

    if (legend.getUserNo() != null) {
      entity.setUser(entityManager.getReference(UserJPAEntity.class, legend.getUserNo()));
    }

    legendJPARepository.save(entity);
  }
}
