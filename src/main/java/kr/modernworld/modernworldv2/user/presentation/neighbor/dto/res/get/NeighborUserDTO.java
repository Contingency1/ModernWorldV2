package kr.modernworld.modernworldv2.user.presentation.neighbor.dto.res.get;

import java.util.List;

public record NeighborUserDTO(
    Long no,
    String nickname,
    String image,
    String description,
    List<UserAchievementWrapperDTO> userAchievement
) {

}
