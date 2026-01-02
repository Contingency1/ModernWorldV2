package kr.modernworld.modernworldv2.asset.presentation.inventory.dto.response;

import java.time.Instant;
import kr.modernworld.modernworldv2.asset.application.inventory.dto.GetInventoryDTO;

public record GetInventoryResponseDTO(
    Long no,
    Long userNo,
    Long itemNo,
    Instant createdAt,
    Boolean status,
    InventoryItemResponseDTO item
) {

  public static GetInventoryResponseDTO from(GetInventoryDTO item) {
    return new GetInventoryResponseDTO(
        item.no(),
        item.userNo(),
        item.itemNo(),
        item.createdAt(),
        item.status(),
        InventoryItemResponseDTO.from(item.item())
    );
  }
}
