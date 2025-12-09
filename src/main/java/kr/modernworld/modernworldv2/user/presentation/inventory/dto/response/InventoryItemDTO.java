package kr.modernworld.modernworldv2.user.presentation.inventory.dto.response;

import kr.modernworld.modernworldv2.admin.domain.item.ItemType;

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
