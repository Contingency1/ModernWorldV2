package kr.modernworld.modernworldv2.asset.domain.inventory.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.asset.domain.item.ItemType;
import kr.modernworld.modernworldv2.asset.presentation.inventory.dto.response.GetInventoryResponseDTO;

public interface InventoryQueryRepository {

  List<GetInventoryResponseDTO> getInventory(Long userNo,
      String theme, Boolean status, String itemName);

  Optional<ItemType> findInventoryItemType(Long userNo, Long itemNo);

  InventoryCollection findInventoryByUserNoAndTypeNo(Long userNo, ItemType type);

  Boolean exists(Long userNo, Long itemNo);

}
