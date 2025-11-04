package kr.modernworld.modernworldv2.user.application;

import java.util.List;
import kr.modernworld.modernworldv2.admin.domain.ItemType;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.Inventory;
import kr.modernworld.modernworldv2.user.domain.InventoryCollection;
import kr.modernworld.modernworldv2.user.domain.port.inventory.InventoryQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.inventory.InventoryRepository;
import kr.modernworld.modernworldv2.user.presentation.inventory.dto.request.GetInventoryRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.inventory.dto.response.GetInventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  private final InventoryQueryRepository inventoryQueryRepository;

  public List<GetInventoryResponseDTO> getAllItems(Long userNo,
      GetInventoryRequestDTO query) {
    return inventoryQueryRepository.getInventory(userNo, query.theme(), query.status(),
        query.itemName());
  }

  @Transactional
  public Inventory addOneItemInInventory(Long userNo, Long itemNo, ItemType itemType) {
    return inventoryRepository.save(Inventory.create(userNo, itemNo, itemType));
  }

  @Transactional(readOnly = true)
  public void validateItemNotExists(Long userNo, Long itemNo) {
    if (inventoryQueryRepository.isExists(userNo, itemNo)) {
      throw new BusinessException(BusinessErrorCode.ITEM_ALREADY_EXISTS_IN_INVENTORY);
    }
  }

  @Transactional
  public Inventory updateItemEquipStatus(Long userNo, Long itemNo, Boolean status) {
    ItemType itemType =
        inventoryQueryRepository.findInventoryItemType(userNo, itemNo)
            .orElseThrow(
                () -> new BusinessException(BusinessErrorCode.ITEM_TYPE_NOT_FOUND_IN_INVENTORY,
                    " itemNo: " + itemNo));

    InventoryCollection userItems =
        inventoryQueryRepository.findInventoryByUserNoAndTypeNo(userNo, itemType);

    Inventory response = userItems.equipOrUnequip(itemNo, status);

    inventoryRepository.update(userItems);

    return response;
  }
}
