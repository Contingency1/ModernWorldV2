package kr.modernworld.modernworldv2.asset.presentation.shop.character.dto.res;

import kr.modernworld.modernworldv2.admin.application.character.api.CharacterApiDTO;

public record ShopCharacterResponseDTO(
    Long no,
    String name,
    String description,
    String image,
    String species,
    Long price
) {

  public static ShopCharacterResponseDTO from(CharacterApiDTO info) {
    return new ShopCharacterResponseDTO(
        info.no(),
        info.name(),
        info.description(),
        info.image(),
        info.species().getName(),
        info.price()
    );
  }
}
