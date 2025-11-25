package kr.modernworld.modernworldv2.admin.domain.port.item;

import java.util.Optional;

public interface ItemQueryRepository {

  Optional<Long> getPrice(Long itemNo);

  Boolean exists(Long itemNo);
}
