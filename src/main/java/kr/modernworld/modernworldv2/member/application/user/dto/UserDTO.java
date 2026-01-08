package kr.modernworld.modernworldv2.member.application.user.dto;

import java.util.Collections;
import java.util.List;

public record UserDTO(
    Long no,
    String socialName,
    String nickname,
    String description,
    Long currentPoint,
    Long accumulationPoint,
    String image,
    UserLegendDTO legend,
    List<UserCharacterLockerDTO> characterLocker,
    List<UserAchievementDTO> userAchievement,
    Long chance
) {

  public UserDTO(
      Long no,
      String socialName,
      String nickname,
      String description,
      Long currentPoint,
      Long accumulationPoint,
      String image,
      UserLegendDTO legend,
      Long chance
  ) {
    this(no, socialName, nickname, description, currentPoint, accumulationPoint, image, legend,
        Collections.emptyList(), Collections.emptyList(), chance);
  }

  public record UserLegendDTO(
      Long likeCount
  ) {

  }

  public record UserCharacterLockerDTO(
      UserCharacterDTO character
  ) {

  }

  public record UserCharacterDTO(
      Long no,
      String image
  ) {

  }

  public record UserAchievementDTO(
      UserAchievementDetailDTO achievement
  ) {

  }

  public record UserAchievementDetailDTO(
      String title,
      String level
  ) {

  }
}
