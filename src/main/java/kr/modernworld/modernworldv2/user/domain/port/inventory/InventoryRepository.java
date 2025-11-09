package kr.modernworld.modernworldv2.user.domain.port.inventory;

import kr.modernworld.modernworldv2.user.domain.Inventory;
import kr.modernworld.modernworldv2.user.domain.InventoryCollection;

public interface InventoryRepository {

  Inventory save(Inventory inventory);

  void update(InventoryCollection items);
}
