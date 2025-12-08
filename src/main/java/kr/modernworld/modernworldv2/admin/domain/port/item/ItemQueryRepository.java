package kr.modernworld.modernworldv2.admin.domain.port.item;

import java.util.Optional;
import kr.modernworld.modernworldv2.admin.application.api.ItemNameAndPriceDTO;

public interface ItemQueryRepository {

  Optional<ItemNameAndPriceDTO> findNameAndPrice(Long itemNo);

  Boolean exists(Long itemNo);
}
