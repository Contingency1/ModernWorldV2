package kr.modernworld.modernworldv2.admin.domain.port.character;

import java.util.Optional;

public interface CharacterQueryRepository {

  Optional<Long> getPrice(Long no);
}
