package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.global.mapper.TimeMapper;
import kr.modernworld.modernworldv2.user.domain.user.User;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TimeMapper.class})
public interface UserMapper {

  @Mapping(source = "token.socialAccess", target = "token.socialAccessToken")
  @Mapping(source = "token.socialRefresh", target = "token.socialRefreshToken")
  User toDomain(UserJPAEntity userJPAEntity);

  @Mapping(source = "token.socialAccessToken", target = "token.socialAccess")
  @Mapping(source = "token.socialRefreshToken", target = "token.socialRefresh")
  UserJPAEntity toEntity(User user);

  @Mapping(source = "token.socialAccessToken", target = "token.socialAccess")
  @Mapping(source = "token.socialRefreshToken", target = "token.socialRefresh")
  void updateUserEntityFromDomain(User user, @MappingTarget UserJPAEntity userJPAEntity);
}
