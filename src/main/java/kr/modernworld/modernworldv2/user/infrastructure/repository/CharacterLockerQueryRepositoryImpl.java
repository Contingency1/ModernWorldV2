package kr.modernworld.modernworldv2.user.infrastructure.repository;

import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.CharacterSpecies;
import kr.modernworld.modernworldv2.user.domain.port.CharacterLockerQueryRepository;
import kr.modernworld.modernworldv2.user.presentation.dto.res.GetCharacterLockerResponseDTO;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterLockerQueryRepositoryImpl implements CharacterLockerQueryRepository {

  @Override
  public List<GetCharacterLockerResponseDTO> getUserCharacters(Long userNo, Boolean status,
      CharacterSpecies species) {
    return null;
  }

  @Override
  public Boolean exists(Long userNo, CharacterSpecies species) {
    return null;
  }
}
