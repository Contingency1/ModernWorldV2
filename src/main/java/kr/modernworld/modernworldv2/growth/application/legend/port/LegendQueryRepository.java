package kr.modernworld.modernworldv2.growth.application.legend.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.growth.domain.legend.Legend;

public interface LegendQueryRepository {

  Optional<Legend> findLegendByUserNo(Long userNo);
}
