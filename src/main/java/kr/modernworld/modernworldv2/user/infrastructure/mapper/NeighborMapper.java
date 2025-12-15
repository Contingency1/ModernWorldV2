package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.neighbor.Neighbor;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.NeighborJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NeighborMapper {

  @Mapping(target = "senderNo", source = "sender.no")
  @Mapping(target = "receiverNo", source = "receiver.no")
  Neighbor toDomain(NeighborJPAEntity entity);

  @Mapping(target = "sender.no", source = "senderNo")
  @Mapping(target = "receiver.no", source = "receiverNo")
  NeighborJPAEntity toEntity(Neighbor domain);
}
