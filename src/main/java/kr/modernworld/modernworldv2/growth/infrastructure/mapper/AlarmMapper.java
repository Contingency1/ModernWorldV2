package kr.modernworld.modernworldv2.growth.infrastructure.mapper;

import kr.modernworld.modernworldv2.growth.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.AlarmJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlarmMapper {

  @Mapping(target = "userNo", source = "user.no")
  Alarm toDomain(AlarmJPAEntity alarm);

  @Mapping(target = "user.no", source = "userNo")
  AlarmJPAEntity toEntity(Alarm alarm);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user.no", source = "userNo")
  void updateEntityFromDomain(Alarm alarm, @MappingTarget AlarmJPAEntity alarmJPAEntity);
}
