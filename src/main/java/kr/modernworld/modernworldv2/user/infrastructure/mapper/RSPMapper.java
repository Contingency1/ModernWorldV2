package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.rsp.RSP;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.RspGameRecordJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RSPMapper {

  @Mapping(target = "user.no", source = "userNo")
  RspGameRecordJPAEntity toEntity(RSP rsp);

  @Mapping(target = "userNo", source = "user.no")
  RSP toDomain(RspGameRecordJPAEntity rsp);

  @Mapping(target = "user.no", source = "userNo")
  void updateEntityFromDomain(RSP rsp, @MappingTarget RspGameRecordJPAEntity entity);
}
