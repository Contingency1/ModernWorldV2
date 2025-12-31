package kr.modernworld.modernworldv2.member.infrastructure.mapper;

import kr.modernworld.modernworldv2.social.domain.neighbor.Neighbor;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.NeighborJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NeighborMapper {

  @Mapping(target = "senderNo", source = "sender.no")
  @Mapping(target = "receiverNo", source = "receiver.no")
  Neighbor toDomain(NeighborJPAEntity entity);

  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  NeighborJPAEntity toEntity(Neighbor domain);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  void updateEntityFromDomain(Neighbor entity, @MappingTarget NeighborJPAEntity domain);
}
