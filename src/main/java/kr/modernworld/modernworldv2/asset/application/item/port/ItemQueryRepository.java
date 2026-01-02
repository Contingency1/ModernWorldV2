package kr.modernworld.modernworldv2.asset.application.item.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemApiDTO;
import kr.modernworld.modernworldv2.asset.application.item.dto.ItemNameAndPriceDTO;

public interface ItemQueryRepository {

  Optional<ItemNameAndPriceDTO> findNameAndPrice(Long itemNo);

  Boolean exists(Long itemNo);

  Optional<ItemApiDTO> findOne(Long itemNo);

  List<ItemApiDTO> findAll(String theme, String itemName);
}
