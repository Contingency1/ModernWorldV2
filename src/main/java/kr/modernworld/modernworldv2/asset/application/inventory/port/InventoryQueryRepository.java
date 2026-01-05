package kr.modernworld.modernworldv2.asset.application.inventory.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.inventory.dto.GetInventoryDTO;
import kr.modernworld.modernworldv2.asset.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.asset.domain.item.ItemType;

public interface InventoryQueryRepository {

  List<GetInventoryDTO> getInventory(Long userNo,
      String theme, Boolean status, String itemName);

  Optional<ItemType> findInventoryItemType(Long userNo, Long itemNo);

  InventoryCollection findInventoryByUserNoAndTypeNo(Long userNo, ItemType type);

  Boolean exists(Long userNo, Long itemNo);

}
