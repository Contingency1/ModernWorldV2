package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.global.mapper.TimeMapper;
import kr.modernworld.modernworldv2.user.domain.user.User;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TimeMapper.class})
public interface UserMapper {

  @Mapping(target = "token", ignore = true)
  User toDomain(UserJPAEntity userJPAEntity);

  UserJPAEntity toEntity(User user);

}
