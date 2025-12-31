package kr.modernworld.modernworldv2.asset.application.item.dto;

import kr.modernworld.modernworldv2.asset.domain.item.ItemType;

public record ItemApiDTO(
    Long no,
    String name,
    String description,
    String image,
    String theme,
    ItemType type,
    Long price
) {

}
