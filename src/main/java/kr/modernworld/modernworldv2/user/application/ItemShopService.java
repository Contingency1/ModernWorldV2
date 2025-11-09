package kr.modernworld.modernworldv2.user.application;

import kr.modernworld.modernworldv2.admin.application.api.ItemApi;
import kr.modernworld.modernworldv2.admin.application.api.ItemPriceAndTypeDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.Inventory;
import kr.modernworld.modernworldv2.user.domain.port.user.UserQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.user.UserRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemShopService {

  private final ItemApi itemApi;
  private final UserQueryRepository userQueryRepository;
  private final InventoryService inventoryService;
  private final UserRepository userRepository;

  @Transactional
  public Inventory buyOneItem(Long userNo, Long itemNo) {
    inventoryService.validateItemNotExists(userNo, itemNo);

    User user = userQueryRepository.findUserCurrentPointByNo(userNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

    ItemPriceAndTypeDTO itemInfo = itemApi.getPrice(itemNo);

    try {
      user.decreaseCurrentPoint(itemInfo.price());
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.USER_NOT_HAS_ENOUGH_POINT,
          " " + e.getMessage());
    }

    userRepository.updateCurrentPoint(user.getNo(), user.getCurrentPoint());
    return inventoryService.addOneItemInInventory(userNo, itemNo, itemInfo.type());
  }

}
