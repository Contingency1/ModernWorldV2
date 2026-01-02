package kr.modernworld.modernworldv2.asset.application.characterlocker.port;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.GetCharacterLockerDTO;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLockerCollection;

public interface CharacterLockerQueryRepository {

  List<GetCharacterLockerDTO> getUserCharactersByCondition(Long userNo, Boolean status,
      CharacterSpecies species);

  CharacterLockerCollection getUserAllCharacters(Long userNo);

  Boolean exists(Long userNo, Long characterNo);
}
