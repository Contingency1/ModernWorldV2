package kr.modernworld.modernworldv2.asset.presentation.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BuyOneItemRequestDTO(
    @NotNull @Min(1) Long itemNo
) {

}
