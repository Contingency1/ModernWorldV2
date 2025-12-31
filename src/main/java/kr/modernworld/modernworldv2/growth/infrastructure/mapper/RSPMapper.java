package kr.modernworld.modernworldv2.growth.infrastructure.mapper;

import kr.modernworld.modernworldv2.growth.domain.rsp.RSP;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.RspGameRecordJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RSPMapper {

  @Mapping(target = "user.no", source = "userNo")
  RspGameRecordJPAEntity toEntity(RSP rsp);

  @Mapping(target = "userNo", source = "user.no")
  RSP toDomain(RspGameRecordJPAEntity rsp);
}
