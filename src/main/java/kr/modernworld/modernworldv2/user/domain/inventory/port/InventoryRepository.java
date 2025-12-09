package kr.modernworld.modernworldv2.user.domain.inventory.port;

import kr.modernworld.modernworldv2.user.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.user.domain.inventory.InventoryCollection;

public interface InventoryRepository {

  Inventory save(Inventory inventory);

  void update(InventoryCollection items);
}
