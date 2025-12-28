package kr.modernworld.modernworldv2.user.presentation.shop.item.dto.res;

import kr.modernworld.modernworldv2.admin.application.item.api.ItemApiDTO;

public record ShopItemResponseDTO(
    Long no,
    String name,
    String description,
    String image,
    String theme,
    String type,
    Long price
) {

  public static ShopItemResponseDTO from(ItemApiDTO input) {
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
