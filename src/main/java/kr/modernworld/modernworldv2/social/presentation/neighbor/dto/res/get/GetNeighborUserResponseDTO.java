package kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res.get;

import java.util.List;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborUserDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.UserAchievementWrapperDTO;

public record GetNeighborUserResponseDTO(
    Long no,
    String nickname,
    String image,
    String description,
    List<UserAchievementWrapperDTO> userAchievement
) {

  public static GetNeighborUserResponseDTO from(GetNeighborUserDTO dto) {
    return new GetNeighborUserResponseDTO(
        dto.no(),
        dto.nickname(),
        dto.image(),
        dto.description(),
        dto.userAchievement()
    );
  }

}
