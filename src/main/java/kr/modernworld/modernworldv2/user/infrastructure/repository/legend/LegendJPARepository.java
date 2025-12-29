package kr.modernworld.modernworldv2.user.infrastructure.repository.legend;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LegendJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegendJPARepository extends JpaRepository<LegendJPAEntity, Long> {

}
