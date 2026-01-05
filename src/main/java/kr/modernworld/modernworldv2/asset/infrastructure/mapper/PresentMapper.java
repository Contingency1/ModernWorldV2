package kr.modernworld.modernworldv2.asset.infrastructure.mapper;

import kr.modernworld.modernworldv2.asset.domain.present.Present;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.PresentJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "item.no", source = "itemNo")
  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  void updateEntityFromDomain(Present domain, @MappingTarget PresentJPAEntity entity);

}
