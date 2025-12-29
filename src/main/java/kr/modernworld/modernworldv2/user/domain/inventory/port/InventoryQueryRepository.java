package kr.modernworld.modernworldv2.user.domain.inventory.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.admin.domain.item.ItemType;
import kr.modernworld.modernworldv2.user.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.user.presentation.inventory.dto.response.GetInventoryResponseDTO;

public interface InventoryQueryRepository {

  List<GetInventoryResponseDTO> getInventory(Long userNo,
      String theme, Boolean status, String itemName);

  Optional<ItemType> findInventoryItemType(Long userNo, Long itemNo);

  InventoryCollection findInventoryByUserNoAndTypeNo(Long userNo, ItemType type);

  Boolean exists(Long userNo, Long itemNo);

}
