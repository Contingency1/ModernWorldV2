package kr.modernworld.modernworldv2.asset.application.inventory;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.inventory.dto.GetInventoryDTO;
import kr.modernworld.modernworldv2.asset.application.inventory.port.InventoryQueryRepository;
import kr.modernworld.modernworldv2.asset.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.asset.domain.inventory.InventoryCollection;
import kr.modernworld.modernworldv2.asset.domain.inventory.port.InventoryRepository;
import kr.modernworld.modernworldv2.asset.domain.item.ItemType;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.growth.application.userachievement.event.IncrementLegendAndCheckAchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;
  private final InventoryQueryRepository inventoryQueryRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public List<GetInventoryDTO> getAllItems(Long userNo,
      String theme, Boolean status, String itemName) {
    return inventoryQueryRepository.getInventory(userNo, theme, status,
        itemName);
  }

  @Transactional
  public Inventory addOneItemInInventory(Long userNo, Long itemNo) {
    applicationEventPublisher.publishEvent(
        new IncrementLegendAndCheckAchievementEvent(this, userNo, LegendField.ITEM_COUNT));

    return inventoryRepository.save(Inventory.create(userNo, itemNo));
  }

  @Transactional(readOnly = true)
  public void validateItemNotExists(Long userNo, Long itemNo) {
    if (inventoryQueryRepository.exists(userNo, itemNo)) {
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
