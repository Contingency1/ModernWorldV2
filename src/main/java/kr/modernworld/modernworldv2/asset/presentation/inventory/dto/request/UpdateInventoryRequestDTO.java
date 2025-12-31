package kr.modernworld.modernworldv2.asset.presentation.inventory.dto.request;


import jakarta.validation.constraints.NotNull;

public record UpdateInventoryRequestDTO(
    @NotNull(message = "status can be true of false.")
    Boolean status
) {

}
