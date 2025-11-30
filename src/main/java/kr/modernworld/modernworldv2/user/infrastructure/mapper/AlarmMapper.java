package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.AlarmJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlarmMapper {

  @Mapping(target = "userNo", source = "user.no")
  Alarm toDomain(AlarmJPAEntity alarm);

  @Mapping(target = "user.no", source = "userNo")
  AlarmJPAEntity toEntity(Alarm alarm);
}
