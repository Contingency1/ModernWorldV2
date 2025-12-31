package kr.modernworld.modernworldv2.asset.domain.inventory.port;

import kr.modernworld.modernworldv2.asset.domain.inventory.Inventory;
import kr.modernworld.modernworldv2.asset.domain.inventory.InventoryCollection;

public interface InventoryRepository {

  Inventory save(Inventory inventory);

  void update(InventoryCollection items);
}
