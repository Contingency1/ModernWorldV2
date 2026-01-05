package kr.modernworld.modernworldv2.asset.application.character.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.character.dto.CharacterDTO;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public interface CharacterQueryRepository {

  Optional<Long> getPrice(Long no);

  Optional<CharacterDTO> findOne(Long no);

  List<CharacterDTO> findAll(CharacterSpecies species, String name);
}
