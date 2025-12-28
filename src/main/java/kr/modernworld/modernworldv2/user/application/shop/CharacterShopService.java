package kr.modernworld.modernworldv2.user.application.shop;

import java.util.List;
import kr.modernworld.modernworldv2.admin.application.character.api.CharacterApiDTO;
import kr.modernworld.modernworldv2.admin.domain.character.CharacterSpecies;
import kr.modernworld.modernworldv2.admin.domain.character.port.CharacterApi;
import kr.modernworld.modernworldv2.user.application.characterlocker.CharacterLockerService;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.user.presentation.shop.character.dto.req.ShopCharacterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterShopService {

  private final CharacterLockerService characterLockerService;
  private final CharacterApi characterApi;
  private final UserService userService;

  @Transactional
  public CharacterLocker buyOneCharacter(Long userNo, Long characterNo) {
    characterLockerService.validateCharacterExists(userNo, characterNo);

    Long characterPrice = characterApi.getPrice(characterNo);

    userService.decreaseCurrentPoint(userNo, characterPrice);

    return characterLockerService.addCharacter(userNo, characterNo);
  }

  @Transactional(readOnly = true)
  public CharacterApiDTO getCharacter(Long characterNo) {
    return characterApi.getOne(characterNo);
  }

  @Transactional(readOnly = true)
  public List<CharacterApiDTO> getCharacters(ShopCharacterRequestDTO query) {
    CharacterSpecies species = CharacterSpecies.stringToCharacterSpecies(query.species());
    String characterName = query.characterName();

    return characterApi.getAll(species, characterName);
  }
}
