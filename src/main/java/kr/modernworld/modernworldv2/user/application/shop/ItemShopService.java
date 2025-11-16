package kr.modernworld.modernworldv2.user.application.shop;

import kr.modernworld.modernworldv2.admin.application.api.ItemApi;
import kr.modernworld.modernworldv2.admin.application.api.ItemPriceAndTypeDTO;
import kr.modernworld.modernworldv2.user.application.InventoryService;
import kr.modernworld.modernworldv2.user.application.PaymentService;
import kr.modernworld.modernworldv2.user.domain.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemShopService {

  private final ItemApi itemApi;
  private final InventoryService inventoryService;
  private final PaymentService paymentService;

  @Transactional
  public Inventory buyOneItem(Long userNo, Long itemNo) {
    inventoryService.validateItemNotExists(userNo, itemNo);

    ItemPriceAndTypeDTO itemInfo = itemApi.getPrice(itemNo);

    paymentService.pay(userNo, itemInfo.price());

    return inventoryService.addOneItemInInventory(userNo, itemNo, itemInfo.type());
  }

}
