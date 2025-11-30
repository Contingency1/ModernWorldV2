package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.Legend;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LegendJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LegendMapper {

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user", ignore = true)
  LegendJPAEntity toEntity(Legend domain);

  Legend toDomain(LegendJPAEntity legendJPAEntity);
}
