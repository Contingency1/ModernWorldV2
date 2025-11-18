package kr.modernworld.modernworldv2.user.domain.characterlocker;

import java.util.List;
import lombok.Getter;

@Getter
public class CharacterLockerCollection {

  List<CharacterLocker> characterLocker;

  public CharacterLockerCollection(List<CharacterLocker> characterLocker) {
    this.characterLocker = characterLocker;
  }

  public CharacterLocker equipOneThenUnequipLeft(Long characterNo) {
    CharacterLocker result = null;

    for (CharacterLocker locker : characterLocker) {
      if (locker.getCharacterNo().equals(characterNo)) {
        locker.equip();
        result = locker;
        continue;
      }

      locker.unequip();
    }

    if (result == null) {
      throw new IllegalStateException("No Character found with characterNo: " + characterNo);
    }

    return result;
  }


}
