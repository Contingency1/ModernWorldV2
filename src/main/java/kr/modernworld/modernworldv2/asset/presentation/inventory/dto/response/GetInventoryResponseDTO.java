package kr.modernworld.modernworldv2.asset.presentation.inventory.dto.response;

import java.time.Instant;

public record GetInventoryResponseDTO(
    Long no,
    Long userNo,
    Long itemNo,
    Instant createdAt,
    Boolean status,
    InventoryItemDTO item
) {

}
