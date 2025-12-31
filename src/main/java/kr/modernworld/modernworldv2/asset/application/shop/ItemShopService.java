package kr.modernworld.modernworldv2.asset.application.shop;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.inventory.InventoryService;
import kr.modernworld.modernworldv2.asset.application.item.ItemService;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemApiDTO;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.asset.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.asset.presentation.shop.item.dto.req.ItemRequestDTO;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemShopService {

  private final ItemService itemService;
  private final InventoryService inventoryService;
  private final UserService userService;

  @Transactional
  public Inventory buyOneItem(Long userNo, Long itemNo) {
    inventoryService.validateItemNotExists(userNo, itemNo);

    ItemNameAndPriceDTO itemInfo = itemService.getNameAndPrice(itemNo);

    userService.decreaseCurrentPoint(userNo, itemInfo.price());

    return inventoryService.addOneItemInInventory(userNo, itemNo);
  }

  @Transactional(readOnly = true)
  public ItemApiDTO getOne(Long itemNo) {
    return itemService.getOne(itemNo);
  }

  @Transactional(readOnly = true)
  public List<ItemApiDTO> getAll(ItemRequestDTO query) {
    return itemService.getAll(query.theme(), query.itemName());
  }
}
