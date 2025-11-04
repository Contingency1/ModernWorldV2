package kr.modernworld.modernworldv2.user.domain.port.inventory;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.admin.domain.ItemType;
import kr.modernworld.modernworldv2.user.domain.InventoryCollection;
import kr.modernworld.modernworldv2.user.presentation.inventory.dto.response.GetInventoryResponseDTO;

public interface InventoryQueryRepository {

  List<GetInventoryResponseDTO> getInventory(Long userNo,
      String theme, Boolean status, String itemName);

  Optional<ItemType> findInventoryItemType(Long userNo, Long itemNo);

  InventoryCollection findInventoryByUserNoAndTypeNo(Long userNo, ItemType type);

  Boolean isExists(Long userNo, Long itemNo);

}
