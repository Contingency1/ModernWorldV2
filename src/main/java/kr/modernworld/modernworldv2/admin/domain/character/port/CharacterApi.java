package kr.modernworld.modernworldv2.admin.domain.character.port;

import java.util.List;
import kr.modernworld.modernworldv2.admin.application.character.api.CharacterApiDTO;
import kr.modernworld.modernworldv2.admin.domain.character.CharacterSpecies;

public interface CharacterApi {

  Long getPrice(Long characterNo);

  CharacterApiDTO getOne(Long characterNo);

  List<CharacterApiDTO> getAll(CharacterSpecies species, String characterName);
}
