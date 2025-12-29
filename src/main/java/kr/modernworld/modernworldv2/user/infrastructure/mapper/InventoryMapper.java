package kr.modernworld.modernworldv2.user.infrastructure.mapper;

import kr.modernworld.modernworldv2.global.mapper.TimeMapper;
import kr.modernworld.modernworldv2.user.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.InventoryJPAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {TimeMapper.class})
public interface InventoryMapper {

  @Mapping(target = "userNo", source = "user.no")
  @Mapping(target = "itemType", source = "item.type")
  @Mapping(target = "itemNo", source = "item.no")
  Inventory toDomain(InventoryJPAEntity inventoryJPAEntity);

  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "item.no", source = "itemNo")
  InventoryJPAEntity toJPAEntity(Inventory inventory);

  @Mapping(target = "no", ignore = true)
  @Mapping(target = "user.no", source = "userNo")
  @Mapping(target = "item.no", source = "itemNo")
  void updateEntityFromDomain(Inventory inventory,
      @MappingTarget InventoryJPAEntity inventoryJPAEntity);

}
