package kr.modernworld.modernworldv2.user.domain.legend.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.legend.Legend;

public interface LegendRepository {

  Legend save(Legend legend);

  Optional<Legend> findByUserNoForUpdate(Long userNo);
}
