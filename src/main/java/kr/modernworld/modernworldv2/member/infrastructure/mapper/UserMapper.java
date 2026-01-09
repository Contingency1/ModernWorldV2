package kr.modernworld.modernworldv2.member.infrastructure.mapper;

import kr.modernworld.modernworldv2.global.mapper.TimeMapper;
import kr.modernworld.modernworldv2.member.domain.user.User;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TimeMapper.class})
public interface UserMapper {

  @Mapping(target = "token", ignore = true)
  User toDomain(UserJPAEntity userJPAEntity);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "userAchievements", ignore = true)
  @Mapping(target = "rspGameRecords", ignore = true)
  @Mapping(target = "inventories", ignore = true)
  @Mapping(target = "characterLockers", ignore = true)
  @Mapping(target = "alarms", ignore = true)
  @Mapping(target = "legend", ignore = true)
  UserJPAEntity toEntity(User user);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "userAchievements", ignore = true)
  @Mapping(target = "rspGameRecords", ignore = true)
  @Mapping(target = "inventories", ignore = true)
  @Mapping(target = "characterLockers", ignore = true)
  @Mapping(target = "alarms", ignore = true)
  @Mapping(target = "legend", ignore = true)
  void updateEntityFromDomain(User user, @MappingTarget UserJPAEntity entity);
}
