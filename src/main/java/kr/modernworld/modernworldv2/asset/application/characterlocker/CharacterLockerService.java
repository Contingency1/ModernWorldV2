package kr.modernworld.modernworldv2.asset.application.characterlocker;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.GetCharacterLockerDTO;
import kr.modernworld.modernworldv2.asset.application.characterlocker.port.CharacterLockerQueryRepository;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLockerCollection;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.port.CharacterLockerRepository;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterLockerService {

  private final CharacterLockerRepository characterLockerRepository;
  private final CharacterLockerQueryRepository characterLockerQueryRepository;

  @Transactional(readOnly = true)
  public List<GetCharacterLockerDTO> getUserCharacters(Long userNo, Boolean status,
      CharacterSpecies species) {
    return characterLockerQueryRepository.getUserCharactersByCondition(userNo, status, species);
  }

  @Transactional(readOnly = true)
  public void validateCharacterExists(Long userNo, Long characterNo) {
    if (characterLockerQueryRepository.exists(userNo, characterNo)) {
      throw new BusinessException(BusinessErrorCode.CHARACTER_ALREADY_EXISTS,
          " characterNo: " + characterNo);
    }
  }

  @Transactional
  public CharacterLocker addCharacter(Long userNo, Long characterNo) {
    return characterLockerRepository.save(CharacterLocker.create(userNo, characterNo));
  }

  @Transactional
  public CharacterLocker addCharacterForNewUser(Long userNo, Long characterNo) {
    if (characterNo >= 1 && characterNo <= 4) {
      return characterLockerRepository.save(CharacterLocker.create(userNo, characterNo));
    }

    throw new BusinessException(BusinessErrorCode.NEW_USER_CAN_GET_ONLY_SPECIAL_CHARACTER);
  }

  @Transactional
  public CharacterLocker equip(Long userNo, Long characterNo) {
    if (!characterLockerQueryRepository.exists(userNo, characterNo)) {
      throw new BusinessException(BusinessErrorCode.CHARACTER_NOT_FOUND_IN_CHARACTER_LOCKER,
          " characterNo: " + characterNo);
    }

    CharacterLockerCollection userCharacters =
        characterLockerQueryRepository.getUserAllCharacters(userNo);

    CharacterLocker equippedCharacterLocker = userCharacters.equipOneThenUnequipLeft(characterNo);

    characterLockerRepository.update(userCharacters);
    return equippedCharacterLocker;
  }

  @Transactional(readOnly = true)
  public Boolean userHasAnyCharacter(Long userNo) {
    return characterLockerQueryRepository.userHasAnyCharacter(userNo);
  }
}
