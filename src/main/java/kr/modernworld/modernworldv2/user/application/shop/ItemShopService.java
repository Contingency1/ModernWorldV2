package kr.modernworld.modernworldv2.user.application.shop;

import kr.modernworld.modernworldv2.admin.application.api.ItemApi;
import kr.modernworld.modernworldv2.admin.application.api.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.user.application.InventoryService;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.domain.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemShopService {

  private final ItemApi itemApi;
  private final InventoryService inventoryService;
  private final UserService userService;

  @Transactional
  public Inventory buyOneItem(Long userNo, Long itemNo) {
    inventoryService.validateItemNotExists(userNo, itemNo);

    ItemNameAndPriceDTO itemInfo = itemApi.getNameAndPrice(itemNo);

    userService.decreaseCurrentPoint(userNo, itemInfo.price());

    return inventoryService.addOneItemInInventory(userNo, itemNo);
  }
}
