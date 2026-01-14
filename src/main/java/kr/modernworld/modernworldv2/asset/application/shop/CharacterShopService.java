package kr.modernworld.modernworldv2.asset.application.shop;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.character.CharacterService;
import kr.modernworld.modernworldv2.asset.application.character.dto.CharacterDTO;
import kr.modernworld.modernworldv2.asset.application.characterlocker.CharacterLockerService;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.asset.domain.external.member.MemberExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterShopService {

  private final CharacterLockerService characterLockerService;
  private final CharacterService characterService;
  private final MemberExternalPort memberExternalPort;

  @Transactional
  public CharacterLocker buyOneCharacter(Long userNo, Long characterNo) {
    memberExternalPort.lockUserByUserNo(userNo);

    Boolean exist = characterLockerService.userHasAnyCharacter(userNo);

    if (!exist) {
      return characterLockerService.addCharacterForNewUser(userNo, characterNo);
    }

    characterLockerService.validateCharacterExists(userNo, characterNo);

    Long characterPrice = characterService.getPrice(characterNo);

    memberExternalPort.decreaseCurrentPoint(userNo, characterPrice);

    return characterLockerService.addCharacter(userNo, characterNo);
  }

  @Transactional(readOnly = true)
  public CharacterDTO getCharacter(Long characterNo) {
    return characterService.getOne(characterNo);
  }

  @Transactional(readOnly = true)
  public List<CharacterDTO> getCharacters(String species, String characterName) {
    CharacterSpecies querySpecies = CharacterSpecies.stringToCharacterSpecies(species);

    return characterService.getAll(querySpecies, characterName);
  }
}
