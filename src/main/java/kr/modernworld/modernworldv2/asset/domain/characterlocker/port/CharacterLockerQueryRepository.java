package kr.modernworld.modernworldv2.asset.domain.characterlocker.port;

import java.util.List;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLockerCollection;
import kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res.GetCharacterLockerResponseDTO;

public interface CharacterLockerQueryRepository {

  List<GetCharacterLockerResponseDTO> getUserCharactersByCondition(Long userNo, Boolean status,
      CharacterSpecies species);

  CharacterLockerCollection getUserAllCharacters(Long userNo);

  Boolean exists(Long userNo, Long characterNo);
}
