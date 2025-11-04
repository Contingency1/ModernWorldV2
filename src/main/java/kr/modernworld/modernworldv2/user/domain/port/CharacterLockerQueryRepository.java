package kr.modernworld.modernworldv2.user.domain.port;

import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.CharacterSpecies;
import kr.modernworld.modernworldv2.user.presentation.dto.res.GetCharacterLockerResponseDTO;

public interface CharacterLockerQueryRepository {

  List<GetCharacterLockerResponseDTO> getUserCharacters(Long userNo, Boolean status,
      CharacterSpecies species);

  Boolean exists(Long userNo, CharacterSpecies species);
}
