package kr.modernworld.modernworldv2.user.application.shop;

import java.util.List;
import kr.modernworld.modernworldv2.admin.application.item.api.ItemApiDTO;
import kr.modernworld.modernworldv2.admin.application.item.api.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.admin.domain.item.port.ItemApi;
import kr.modernworld.modernworldv2.user.application.inventory.InventoryService;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.user.presentation.shop.item.dto.req.ItemRequestDTO;
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

  @Transactional(readOnly = true)
  public ItemApiDTO getOne(Long itemNo) {
    return itemApi.getOne(itemNo);
  }

  @Transactional(readOnly = true)
  public List<ItemApiDTO> getAll(ItemRequestDTO query) {
    return itemApi.getAll(query.theme(), query.itemName());
  }
}
