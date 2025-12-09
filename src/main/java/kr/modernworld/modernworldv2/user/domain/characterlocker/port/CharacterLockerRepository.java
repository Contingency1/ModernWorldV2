package kr.modernworld.modernworldv2.user.domain.characterlocker.port;

import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLockerCollection;

public interface CharacterLockerRepository {

  CharacterLocker save(CharacterLocker characterLocker);

  void update(CharacterLockerCollection characterLockerCollection);
}
