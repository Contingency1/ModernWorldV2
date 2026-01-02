package kr.modernworld.modernworldv2.asset.application.inventory.dto;

import kr.modernworld.modernworldv2.asset.domain.item.ItemType;

public record InventoryItemDTO(
    Long no,
    String name,
    String description,
    String image,
    String theme,
    ItemType type,
    Long price
) {

}
