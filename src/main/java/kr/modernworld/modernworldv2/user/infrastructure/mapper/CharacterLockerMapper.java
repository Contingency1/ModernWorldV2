package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterLockerMapper {

  @Mapping(target = "userNo", source = "user.no")
  @Mapping(target = "characterNo", source = "character.no")
  CharacterLocker toDomain(CharacterLockerJPAEntity entity);

  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "character.no", source = "characterNo")
  CharacterLockerJPAEntity toJPAEntity(CharacterLocker domain);
}
