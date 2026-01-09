package kr.modernworld.modernworldv2.member.presentation.user.dto.res;

import java.util.List;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserAchievementDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserAchievementDetailDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserCharacterDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserCharacterLockerDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO.UserLegendDTO;

public record UserResponseDTO(
    Long no,
    String socialName,
    String nickname,
    String description,
    Long currentPoint,
    Long accumulationPoint,
    String image,
    UserLegendResponseDTO legend,
    List<UserCharacterLockerResponseDTO> characterLocker,
    List<UserAchievementResponseDTO> userAchievement,
    Long chance
) {

  public static UserResponseDTO from(UserDTO dto) {
    return new UserResponseDTO(
        dto.no(),
        dto.socialName(),
        dto.nickname(),
        dto.description(),
        dto.currentPoint(),
        dto.accumulationPoint(),
        dto.image(),
        UserLegendResponseDTO.from(dto.legend()),
        dto.characterLocker().stream().map(UserCharacterLockerResponseDTO::from).toList(),
        dto.userAchievement().stream().map(UserAchievementResponseDTO::from).toList(),
        dto.chance()
    );
  }

  public record UserLegendResponseDTO(
      Long likeCount
  ) {

    public static UserLegendResponseDTO from(UserLegendDTO likeCount) {
      return new UserLegendResponseDTO(likeCount.likeCount());
    }
  }

  public record UserCharacterLockerResponseDTO(
      UserCharacterResponseDTO character
  ) {

    public static UserCharacterLockerResponseDTO from(UserCharacterLockerDTO dto) {
      return new UserCharacterLockerResponseDTO(UserCharacterResponseDTO.from(dto.character()));
    }
  }

  public record UserCharacterResponseDTO(
      Long no,
      String image
  ) {

    public static UserCharacterResponseDTO from(UserCharacterDTO dto) {
      return new UserCharacterResponseDTO(dto.no(), dto.image());
    }
  }

  public record UserAchievementResponseDTO(
      UserAchievementDetailResponseDTO achievement
  ) {

    public static UserAchievementResponseDTO from(UserAchievementDTO dto) {
      return new UserAchievementResponseDTO(
          UserAchievementDetailResponseDTO.from(dto.achievement()));
    }
  }

  public record UserAchievementDetailResponseDTO(
      String title,
      String level
  ) {

    public static UserAchievementDetailResponseDTO from(UserAchievementDetailDTO dto) {
      return new UserAchievementDetailResponseDTO(dto.title(), dto.level());
    }
  }

}
