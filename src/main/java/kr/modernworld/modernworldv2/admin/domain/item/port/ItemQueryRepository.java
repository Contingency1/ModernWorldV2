package kr.modernworld.modernworldv2.admin.domain.item.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.admin.application.item.api.ItemNameAndPriceDTO;

public interface ItemQueryRepository {

  Optional<ItemNameAndPriceDTO> findNameAndPrice(Long itemNo);

  Boolean exists(Long itemNo);
}
