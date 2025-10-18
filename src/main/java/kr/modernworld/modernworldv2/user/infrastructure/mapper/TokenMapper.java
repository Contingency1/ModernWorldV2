package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.user.UserToken;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.TokenJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TokenMapper {

  @Mapping(target = "no", source = "userNo")
  @Mapping(source = "socialAccessToken", target = "socialAccess")
  @Mapping(source = "socialRefreshToken", target = "socialRefresh")
  TokenJPAEntity toEntity(UserToken userToken);

  @Mapping(source = "user.no", target = "userNo")
  @Mapping(source = "socialAccess", target = "socialAccessToken")
  @Mapping(source = "socialRefresh", target = "socialRefreshToken")
  UserToken toDomain(TokenJPAEntity tokenJPAEntity);
}
