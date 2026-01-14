package kr.modernworld.modernworldv2.social.presentation.rsp;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.social.application.rsp.RSPService;
import kr.modernworld.modernworldv2.social.application.rsp.dto.GetRSPDTO;
import kr.modernworld.modernworldv2.social.presentation.rsp.dto.req.GetRSPRecordsRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.rsp.dto.req.RSPRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.rsp.dto.res.RSPResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class RSPController {

  private final RSPService rspService;

  @GetMapping("/{userNo}/rock-scissors-paper")
  public ResponseEntity<List<RSPResponseDTO>> get(@PathVariable Long userNo,
      GetRSPRecordsRequestDTO query) {
    List<GetRSPDTO> response = rspService.get(userNo, query.date());

    return new ResponseEntity<>(
        response.stream().map(RSPResponseDTO::from).toList(),
        HttpStatus.OK);
  }

  @PostMapping("/my/rock-scissors-paper")
  public ResponseEntity<RSPResponseDTO> create(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid @RequestBody RSPRequestDTO body) {
    GetRSPDTO response = rspService.create(user.userNo(), body.choice());

    return new ResponseEntity<>(RSPResponseDTO.from(response), HttpStatus.CREATED);
  }

}
