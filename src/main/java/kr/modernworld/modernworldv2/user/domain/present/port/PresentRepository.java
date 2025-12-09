package kr.modernworld.modernworldv2.user.domain.present.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.present.Present;

public interface PresentRepository {

  Present save(Present present);

  Optional<Present> findByNoForUpdate(Long userNo, Long presentNo);

}
