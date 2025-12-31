package kr.modernworld.modernworldv2.asset.presentation.inventory.dto.request;

public record GetInventoryRequestDTO(
    String theme,
    Boolean status,
    String itemName
) {

}
