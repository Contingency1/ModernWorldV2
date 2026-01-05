package kr.modernworld.modernworldv2.asset.domain.characterlocker.port;

import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLockerCollection;

public interface CharacterLockerRepository {

  CharacterLocker save(CharacterLocker characterLocker);

  void update(CharacterLockerCollection characterLockerCollection);
}
