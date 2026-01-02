package kr.modernworld.modernworldv2.asset.application.character.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.character.dto.CharacterApiDTO;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public interface CharacterQueryRepository {

  Optional<Long> getPrice(Long no);

  Optional<CharacterApiDTO> findOne(Long no);

  List<CharacterApiDTO> findAll(CharacterSpecies species, String name);
}
