package kr.modernworld.modernworldv2.user.application.shop;

import kr.modernworld.modernworldv2.admin.application.api.CharacterApi;
import kr.modernworld.modernworldv2.user.application.characterlocker.CharacterLockerService;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLocker;
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
}
