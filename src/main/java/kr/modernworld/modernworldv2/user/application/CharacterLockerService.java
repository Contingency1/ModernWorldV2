package kr.modernworld.modernworldv2.user.application;

import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.CharacterSpecies;
import kr.modernworld.modernworldv2.user.domain.port.CharacterLockerQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.CharacterLockerRepository;
import kr.modernworld.modernworldv2.user.presentation.dto.res.GetCharacterLockerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterLockerService {

  private final CharacterLockerRepository characterLockerRepository;
  private final CharacterLockerQueryRepository characterLockerQueryRepository;

  public List<GetCharacterLockerResponseDTO> getUserCharacters(Long userNo, Boolean status,
      CharacterSpecies species) {
    return characterLockerQueryRepository.getUserCharacters(userNo, status, species);
  }

  public void createUserCharacter(Long userNo, Long characterNo) {

  }

  public void equip(Long userNo, Long characterNo) {

  }
}
