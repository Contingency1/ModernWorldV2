package kr.modernworld.modernworldv2.user.presentation.present;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.user.application.PresentService;
import kr.modernworld.modernworldv2.user.domain.present.Present;
import kr.modernworld.modernworldv2.user.infrastructure.repository.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.user.presentation.present.dto.req.ItemNoRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.present.dto.req.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.present.dto.res.GetPresentResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.present.dto.res.PresentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    return new ResponseEntity<>(presentService.getUserPresents(user.userNo(), type), HttpStatus.OK);
  }

  @GetMapping("/my/presents/{presentNo}")
  public ResponseEntity<GetPresentResponseDTO> getMyOnePresent(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable Long presentNo) {

    return new ResponseEntity<>(presentService.getOnePresent(user.userNo(), presentNo),
        HttpStatus.OK);
  }

  @PatchMapping("/my/presents/{presentNo}")
  public ResponseEntity<PresentResponseDTO> acceptOrReject(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable Long presentNo,
      HandlePresentStatus presentStatus) {
    Present response = presentService.acceptOrReject(user.userNo(), presentNo, presentStatus);

    return new ResponseEntity<>(
        new PresentResponseDTO(response.getNo(), response.getItemNo(), response.getSenderNo(),
            response.getReceiverNo(), response.getCreatedAt(), response.getStatus()),
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
      @Valid ItemNoRequestDTO item
  ) {
    Present response = presentService.createOnePresent(user.userNo(), receiverNo, item.itemNo());

    return new ResponseEntity<>(
        new PresentResponseDTO(response.getNo(), response.getItemNo(), response.getSenderNo(),
            response.getReceiverNo(), response.getCreatedAt(), response.getStatus()),
        HttpStatus.CREATED);
  }


}
