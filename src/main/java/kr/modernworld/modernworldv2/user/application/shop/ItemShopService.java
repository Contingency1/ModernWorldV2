package kr.modernworld.modernworldv2.user.application.shop;

import kr.modernworld.modernworldv2.admin.application.api.ItemApi;
import kr.modernworld.modernworldv2.user.application.InventoryService;
import kr.modernworld.modernworldv2.user.application.UserPointService;
import kr.modernworld.modernworldv2.user.domain.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemShopService {

  private final ItemApi itemApi;
  private final InventoryService inventoryService;
  private final UserPointService userPointService;

  @Transactional
  public Inventory buyOneItem(Long userNo, Long itemNo) {
    inventoryService.validateItemNotExists(userNo, itemNo);

    Long itemPrice = itemApi.getPrice(itemNo);

    userPointService.decreaseCurrentPoint(userNo, itemPrice);

    return inventoryService.addOneItemInInventory(userNo, itemNo);
  }

}
