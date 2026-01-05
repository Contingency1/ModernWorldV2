package kr.modernworld.modernworldv2.asset.infrastructure.mapper;

import kr.modernworld.modernworldv2.asset.domain.characterlocker.CharacterLocker;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CharacterLockerMapper {

  @Mapping(target = "userNo", source = "user.no")
  @Mapping(target = "characterNo", source = "character.no")
  CharacterLocker toDomain(CharacterLockerJPAEntity entity);

  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "character.no", source = "characterNo")
  CharacterLockerJPAEntity toJPAEntity(CharacterLocker domain);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "character.no", source = "characterNo")
  void updateEntityFromDomain(CharacterLocker domain,
      @MappingTarget CharacterLockerJPAEntity entity);
}
