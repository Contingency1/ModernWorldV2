package kr.modernworld.modernworldv2.asset.application.shop;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.character.CharacterService;
import kr.modernworld.modernworldv2.asset.application.character.dto.CharacterApiDTO;
import kr.modernworld.modernworldv2.asset.application.characterlocker.CharacterLockerService;
import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.asset.domain.external.member.MemberExternalPort;
import kr.modernworld.modernworldv2.asset.presentation.shop.character.dto.req.ShopCharacterRequestDTO;
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
    characterLockerService.validateCharacterExists(userNo, characterNo);

    Long characterPrice = characterService.getPrice(characterNo);

    memberExternalPort.decreaseCurrentPoint(userNo, characterPrice);

    return characterLockerService.addCharacter(userNo, characterNo);
  }

  @Transactional(readOnly = true)
  public CharacterApiDTO getCharacter(Long characterNo) {
    return characterService.getOne(characterNo);
  }

  @Transactional(readOnly = true)
  public List<CharacterApiDTO> getCharacters(ShopCharacterRequestDTO query) {
    CharacterSpecies species = CharacterSpecies.stringToCharacterSpecies(query.species());
    String characterName = query.characterName();

    return characterService.getAll(species, characterName);
  }
}
