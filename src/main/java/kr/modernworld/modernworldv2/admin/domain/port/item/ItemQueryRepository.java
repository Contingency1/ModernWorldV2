package kr.modernworld.modernworldv2.admin.domain.port.item;

import java.util.Optional;
import kr.modernworld.modernworldv2.admin.application.api.ItemPriceAndTypeDTO;

public interface ItemQueryRepository {

  Optional<ItemPriceAndTypeDTO> getPriceAndType(Long itemNo);

}
