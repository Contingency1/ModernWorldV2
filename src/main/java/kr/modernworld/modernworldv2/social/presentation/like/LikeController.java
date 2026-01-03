package kr.modernworld.modernworldv2.social.presentation.like;

import java.util.List;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.social.application.like.LikeService;
import kr.modernworld.modernworldv2.social.application.like.port.LikeDTO;
import kr.modernworld.modernworldv2.social.presentation.like.dto.req.GetLikeRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.like.dto.res.LikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class LikeController {

  private final LikeService likeService;

  @GetMapping("/{userNo}/likes")
  public ResponseEntity<List<? extends kr.modernworld.modernworldv2.social.presentation.like.dto.res.get.LikeResponseDTO>> getAll(
      @PathVariable Long userNo,
      GetLikeRequestDTO query) {
    List<? extends kr.modernworld.modernworldv2.social.presentation.like.dto.res.get.LikeResponseDTO> response = likeService.getAll(
        userNo, query.type());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/{userNo}/likes")
  public ResponseEntity<LikeResponseDTO> create(
      @AuthenticationPrincipal TokenUserInfoDTO sender,
      @PathVariable("userNo") Long receiverNo) {
    LikeDTO response = likeService.createOne(sender.userNo(), receiverNo);

    return new ResponseEntity<>(LikeResponseDTO.from(response), HttpStatus.CREATED);
  }

  @DeleteMapping("/{userNo}/likes")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal TokenUserInfoDTO sender,
      @PathVariable("userNo") Long receiverNo) {
    likeService.deleteOne(sender.userNo(), receiverNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // 아 URI 진짜 맘에 안드네
  @GetMapping("/my/likes/{receiverNo}")
  public ResponseEntity<Boolean> checkLike(@AuthenticationPrincipal TokenUserInfoDTO sender,
      @PathVariable Long receiverNo) {
    return new ResponseEntity<>(likeService.getOne(sender.userNo(), receiverNo), HttpStatus.OK);
  }
}
