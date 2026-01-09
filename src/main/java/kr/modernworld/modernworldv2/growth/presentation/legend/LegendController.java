package kr.modernworld.modernworldv2.growth.presentation.legend;

import kr.modernworld.modernworldv2.growth.application.legend.LegendService;
import kr.modernworld.modernworldv2.growth.application.legend.dto.LegendDTO;
import kr.modernworld.modernworldv2.growth.presentation.legend.dto.res.LegendResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/my")
public class LegendController {

  private final LegendService legendService;

  @GetMapping("/legends")
  public ResponseEntity<LegendResponseDTO> getLegend(
      @AuthenticationPrincipal TokenUserInfoDTO user) {
    LegendDTO response = legendService.getLegend(user.userNo());

    return new ResponseEntity<>(LegendResponseDTO.from(response), HttpStatus.OK);
  }

}
