package kr.modernworld.modernworldv2.admin.domain.item.port;

import java.util.List;
import kr.modernworld.modernworldv2.admin.application.item.api.ItemApiDTO;
import kr.modernworld.modernworldv2.admin.application.item.api.ItemNameAndPriceDTO;

public interface ItemApi {

  ItemNameAndPriceDTO getNameAndPrice(Long itemNo);

  List<ItemApiDTO> getAll(String theme, String itemName);

  ItemApiDTO getOne(Long itemNo);
}
