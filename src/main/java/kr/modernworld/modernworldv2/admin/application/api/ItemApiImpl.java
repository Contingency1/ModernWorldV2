package kr.modernworld.modernworldv2.admin.application.api;

import kr.modernworld.modernworldv2.admin.domain.port.item.ItemQueryRepository;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemApiImpl implements ItemApi {

  private final ItemQueryRepository itemQueryRepository;

  @Override
  @Transactional(readOnly = true)
  public ItemNameAndPriceDTO getNameAndPrice(Long itemNo) {
    return itemQueryRepository.findNameAndPrice(itemNo).orElseThrow(
        () -> new BusinessException(BusinessErrorCode.ITEM_NOT_FOUND, " itemNo: " + itemNo));
  }
}
