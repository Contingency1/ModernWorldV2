package kr.modernworld.modernworldv2.user.domain.port;

import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.CharacterSpecies;
import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLockerCollection;
import kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.res.GetCharacterLockerResponseDTO;

public interface CharacterLockerQueryRepository {

  List<GetCharacterLockerResponseDTO> getUserCharactersByCondition(Long userNo, Boolean status,
      CharacterSpecies species);

  CharacterLockerCollection getUserAllCharacters(Long userNo);

  Boolean exists(Long userNo, Long characterNo);
}
