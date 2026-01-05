package kr.modernworld.modernworldv2.asset.presentation.inventory.dto.response;

import kr.modernworld.modernworldv2.asset.application.inventory.dto.InventoryItemDTO;

public record InventoryItemResponseDTO(
    Long no,
    String name,
    String description,
    String image,
    String theme,
    String type,
    Long price
) {

  public static InventoryItemResponseDTO from(InventoryItemDTO itemDTO) {
    return new InventoryItemResponseDTO(
        itemDTO.no(),
        itemDTO.name(),
        itemDTO.description(),
        itemDTO.image(),
        itemDTO.theme(),
        itemDTO.type().getStr(),
        itemDTO.price()
    );
  }

}
