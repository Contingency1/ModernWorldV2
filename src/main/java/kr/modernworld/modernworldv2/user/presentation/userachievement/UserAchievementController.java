package kr.modernworld.modernworldv2.user.presentation.userachievement;

import java.util.List;
import kr.modernworld.modernworldv2.user.application.userachievement.UserAchievementService;
import kr.modernworld.modernworldv2.user.domain.UserAchievement;
import kr.modernworld.modernworldv2.user.infrastructure.repository.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.user.presentation.userachievement.dto.req.GetUserAchievementRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.userachievement.dto.req.UpdateUserAchievementStatusDTO;
import kr.modernworld.modernworldv2.user.presentation.userachievement.dto.res.UserAchievementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/my")
@RequiredArgsConstructor
public class UserAchievementController {

  private final UserAchievementService userAchievementService;

  @GetMapping("/achievements")
  public ResponseEntity<List<UserAchievementResponseDTO>> getAchievements(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      GetUserAchievementRequestDTO query) {
    List<UserAchievementResponseDTO> response = userAchievementService.getUserAchievements(
        user.userNo(), query);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/achievements/{achievementNo}")
  public ResponseEntity<UserAchievement> updateAchievement(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long achievementNo,
      UpdateUserAchievementStatusDTO body) {

    UserAchievement response = userAchievementService.updateUserAchievementStatus(
        user.userNo(), achievementNo, body.status());

    return new ResponseEntity<>(response, HttpStatus.OK);

  }

}
