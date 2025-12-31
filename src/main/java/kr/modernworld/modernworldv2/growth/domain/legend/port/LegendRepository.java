package kr.modernworld.modernworldv2.growth.domain.legend.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.growth.domain.legend.Legend;

public interface LegendRepository {

  Legend save(Legend legend);

  Optional<Legend> findByUserNoForUpdate(Long userNo);
}
