package kr.modernworld.modernworldv2.growth.presentation.achievement;

import java.util.List;
import kr.modernworld.modernworldv2.growth.application.achievement.AchievementService;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementDTO;
import kr.modernworld.modernworldv2.growth.presentation.achievement.dto.res.AchievementResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/achievements")
public class AchievementController {

  private final AchievementService achievementService;

  @GetMapping
  public ResponseEntity<List<AchievementResponseDTO>> getAll() {
    List<AchievementDTO> response = achievementService.getAll();

    return new ResponseEntity<>(
        response
            .stream()
            .map(AchievementResponseDTO::from)
            .toList(),
        HttpStatus.OK);
  }

}
