package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.like.Like;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LikeJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

  @Mapping(target = "senderNo", source = "sender.no")
  @Mapping(target = "receiverNo", source = "receiver.no")
  Like toDomain(LikeJPAEntity entity);

  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  LikeJPAEntity toEntity(Like domain);
}
