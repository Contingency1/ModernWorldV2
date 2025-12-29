package kr.modernworld.modernworldv2.user.presentation.inventory.dto.response;

import java.time.Instant;

public record InventoryResponseDTO(
    Long no,
    Long userNo,
    Long itemNo,
    Instant createdAt,
    Boolean status
) {

}
