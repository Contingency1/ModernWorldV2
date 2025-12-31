package kr.modernworld.modernworldv2.asset.application.item;

import java.util.List;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemApiDTO;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemNameAndPriceDTO;
import kr.modernworld.modernworldv2.asset.domain.item.port.ItemQueryRepository;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemQueryRepository itemQueryRepository;

  @Transactional(readOnly = true)
  public ItemNameAndPriceDTO getNameAndPrice(Long itemNo) {
    return itemQueryRepository.findNameAndPrice(itemNo).orElseThrow(
        () -> new BusinessException(BusinessErrorCode.ITEM_NOT_FOUND, " itemNo: " + itemNo));
  }

  @Transactional(readOnly = true)
  public ItemApiDTO getOne(Long itemNo) {
    return itemQueryRepository.findOne(itemNo).orElseThrow(
        () -> new BusinessException(BusinessErrorCode.ITEM_NOT_FOUND, " itemNo: " + itemNo));
  }

  @Transactional(readOnly = true)
  public List<ItemApiDTO> getAll(String theme, String itemName) {
    return itemQueryRepository.findAll(theme, itemName);
  }

}
