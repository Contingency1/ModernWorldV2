package kr.modernworld.modernworldv2.user.presentation.inventory.dto.request;

public record GetInventoryRequestDTO(
    String theme,
    Boolean status,
    String itemName
) {

}
