package kr.modernworld.modernworldv2.user.infrastructure.repository.characterlocker;

import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLockerCollection;
import kr.modernworld.modernworldv2.user.domain.characterlocker.port.CharacterLockerRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.CharacterLockerMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class CharacterLockerRepositoryImpl implements CharacterLockerRepository {

  private final CharacterLockerJPARepository characterLockerJPARepository;
  private final CharacterLockerMapper characterLockerMapper;

  @Override
  public CharacterLocker save(CharacterLocker characterLocker) {

    CharacterLockerJPAEntity save = characterLockerJPARepository.save(
        characterLockerMapper.toJPAEntity(characterLocker));

    return characterLockerMapper.toDomain(save);
  }

  @Override
  public void update(CharacterLockerCollection characterLockerCollection) {

    characterLockerJPARepository.saveAll(characterLockerCollection.getCharacterLocker().stream()
        .map(characterLockerMapper::toJPAEntity).toList());
  }
}
