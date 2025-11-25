package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.present.Present;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.PresentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PresentMapper {

  @Mapping(target = "senderNo", source = "sender.no")
  @Mapping(target = "receiverNo", source = "receiver.no")
  @Mapping(target = "itemNo", source = "item.no")
  Present toDomain(PresentJPAEntity entity);

  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  @Mapping(target = "item.no", source = "itemNo")
  PresentJPAEntity toEntity(Present domain);

}
