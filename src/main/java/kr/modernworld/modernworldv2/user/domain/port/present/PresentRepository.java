package kr.modernworld.modernworldv2.user.domain.port.present;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.present.Present;

public interface PresentRepository {

  Present save(Present present);

  Optional<Present> findByNoForUpdate(Long presentNo);

}
