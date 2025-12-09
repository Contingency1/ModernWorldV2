package kr.modernworld.modernworldv2.admin.domain.character.port;

import java.util.Optional;

public interface CharacterQueryRepository {

  Optional<Long> getPrice(Long no);
}
