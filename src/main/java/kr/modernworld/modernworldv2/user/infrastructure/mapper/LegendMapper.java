package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.user.domain.legend.Legend;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LegendJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LegendMapper {

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user", ignore = true)
  LegendJPAEntity toEntity(Legend domain);

  @Mapping(target = "userNo", source = "user.no")
  Legend toDomain(LegendJPAEntity legendJPAEntity);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user", ignore = true)
  void updateEntityFromDomain(Legend domain, @MappingTarget LegendJPAEntity legendJPAEntity);
}
