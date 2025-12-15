package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.user.UserSocialToken;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.TokenJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocialTokenMapper {

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user.no", source = "userNo")
  @Mapping(source = "socialAccessToken", target = "socialAccess")
  @Mapping(source = "socialRefreshToken", target = "socialRefresh")
  TokenJPAEntity toEntity(UserSocialToken userSocialToken);

  @Mapping(source = "user.no", target = "userNo")
  @Mapping(source = "socialAccess", target = "socialAccessToken")
  @Mapping(source = "socialRefresh", target = "socialRefreshToken")
  UserSocialToken toDomain(TokenJPAEntity tokenJPAEntity);
}
