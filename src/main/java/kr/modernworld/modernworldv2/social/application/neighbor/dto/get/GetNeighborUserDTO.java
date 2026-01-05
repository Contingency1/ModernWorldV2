package kr.modernworld.modernworldv2.social.application.neighbor.dto.get;

import java.util.List;

public record GetNeighborUserDTO(
    Long no,
    String nickname,
    String image,
    String description,
    List<UserAchievementWrapperDTO> userAchievement
) {

}
