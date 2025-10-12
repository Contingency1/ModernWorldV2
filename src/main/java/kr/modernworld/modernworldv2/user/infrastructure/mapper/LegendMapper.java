package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.user.UserLegend;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LegendJPAEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LegendMapper {

  LegendJPAEntity toEntity(UserLegend userLegend);

  UserLegend toDomain(LegendJPAEntity legendJPAEntity);
}
