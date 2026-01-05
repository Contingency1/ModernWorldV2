package kr.modernworld.modernworldv2.asset.presentation.present.dto.res;

import kr.modernworld.modernworldv2.asset.application.present.dto.PresentItemDTO;

public record PresentItemResponseDTO(
    String name,
    String image,
    String description
) {

  public static PresentItemResponseDTO from(PresentItemDTO item) {
    return new PresentItemResponseDTO(
        item.name(),
        item.image(),
        item.description());
  }

}
