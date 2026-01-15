package kr.modernworld.modernworldv2.asset.presentation.present;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.asset.application.present.PresentService;
import kr.modernworld.modernworldv2.asset.application.present.dto.GetPresentDTO;
import kr.modernworld.modernworldv2.asset.domain.present.Present;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.req.ItemNoRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.req.PatchPresentRequestDTO;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.res.GetPresentResponseDTO;
import kr.modernworld.modernworldv2.asset.presentation.present.dto.res.PresentResponseDTO;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PresentController {

  private final PresentService presentService;

  @GetMapping("/my/presents")
  public ResponseEntity<List<GetPresentResponseDTO>> getMyPresents(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      SenderReceiverNoField type
  ) {
    List<GetPresentDTO> response = presentService.getUserPresents(user.userNo(), type);

    return new ResponseEntity<>(
        response.stream().map(GetPresentResponseDTO::from).toList(),
        HttpStatus.OK);
  }

  @GetMapping("/my/presents/{presentNo}")
  public ResponseEntity<GetPresentResponseDTO> getMyOnePresent(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable Long presentNo) {
    GetPresentDTO response = presentService.getOnePresent(user.userNo(), presentNo);

    return new ResponseEntity<>(
        GetPresentResponseDTO.from(response), HttpStatus.OK);
  }

  @PatchMapping("/my/presents/{presentNo}")
  public ResponseEntity<PresentResponseDTO> acceptOrReject(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable Long presentNo,
      @Valid @RequestBody PatchPresentRequestDTO body) {
    Present response = presentService.acceptOrReject(user.userNo(), presentNo, body.status());

    return new ResponseEntity<>(
        new PresentResponseDTO(response.getNo(), response.getItemNo(), response.getSenderNo(),
            response.getReceiverNo(), response.getCreatedAt(), response.getStatus().toString()),
        HttpStatus.OK);
  }

  @DeleteMapping("/my/presents/{presentNo}")
  public ResponseEntity<Void> deleteMyOnePresent(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long presentNo) {
    presentService.deleteOnePresent(user.userNo(), presentNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/{userNo}/presents")
  public ResponseEntity<PresentResponseDTO> giveOnePresent(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable("userNo") Long receiverNo,
      @Valid @RequestBody ItemNoRequestDTO item
  ) {
    Present response = presentService.createOnePresent(user.userNo(), receiverNo, item.itemNo());

    return new ResponseEntity<>(
        new PresentResponseDTO(response.getNo(), response.getItemNo(), response.getSenderNo(),
            response.getReceiverNo(), response.getCreatedAt(), response.getStatus().toString()),
        HttpStatus.CREATED);
  }


}
