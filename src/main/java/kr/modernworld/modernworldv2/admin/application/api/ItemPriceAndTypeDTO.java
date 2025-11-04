package kr.modernworld.modernworldv2.admin.application.api;

import kr.modernworld.modernworldv2.admin.domain.ItemType;

public record ItemPriceAndTypeDTO(
    Long price,
    ItemType type
) {

}
