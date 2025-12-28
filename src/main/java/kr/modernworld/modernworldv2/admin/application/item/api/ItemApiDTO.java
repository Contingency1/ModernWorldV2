package kr.modernworld.modernworldv2.admin.application.item.api;

import kr.modernworld.modernworldv2.admin.domain.item.ItemType;

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
