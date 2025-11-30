package kr.modernworld.modernworldv2.user.domain.port.legend;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.Legend;

public interface LegendQueryRepository {

  Optional<Legend> findLegendByUserNo(Long userNo);
}
