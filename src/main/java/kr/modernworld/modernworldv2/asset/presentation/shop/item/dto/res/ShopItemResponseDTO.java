package kr.modernworld.modernworldv2.asset.presentation.shop.item.dto.res;

import kr.modernworld.modernworldv2.asset.application.item.dto.ItemDTO;

public record ShopItemResponseDTO(
    Long no,
    String name,
    String description,
    String image,
    String theme,
    String type,
    Long price
) {

  public static ShopItemResponseDTO from(ItemDTO input) {
    return new ShopItemResponseDTO(
        input.no(),
        input.name(),
        input.description(),
        input.image(),
        input.theme(),
        input.type().getStr(),
        input.price()
    );
  }
}
