package kr.modernworld.modernworldv2.asset.application.inventory.dto;

import java.time.Instant;

public record GetInventoryDTO(
    Long no,
    Long userNo,
    Long itemNo,
    Instant createdAt,
    Boolean status,
    InventoryItemDTO item
) {

}
