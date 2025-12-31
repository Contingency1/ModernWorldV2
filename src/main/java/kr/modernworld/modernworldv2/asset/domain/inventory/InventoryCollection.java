package kr.modernworld.modernworldv2.asset.domain.inventory;

import java.util.List;
import lombok.Getter;

@Getter
public class InventoryCollection {

  public InventoryCollection(List<Inventory> inventory) {
    this.inventory = inventory;
  }

  private final List<Inventory> inventory;

  public Inventory equipOrUnequip(Long itemNo, Boolean status) {
    Inventory result = null;

    for (Inventory inventory : inventory) {
      if (inventory.getItemNo().equals(itemNo)) {
        if (status) {
          inventory.makeStatusTrue();
        } else {
          inventory.makeStatusFalse();
        }

        result = inventory;
      } else {
        inventory.makeStatusFalse();
      }
    }

    if (result == null) {
      throw new IllegalStateException("Inventory does not contain item no: " + itemNo);
    }

    return result;
  }

}
