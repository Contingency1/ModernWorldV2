package kr.modernworld.modernworldv2.social.presentation.reply;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.social.application.reply.ReplyService;
import kr.modernworld.modernworldv2.social.application.reply.dto.ReplyDTO;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.req.CreateReplyRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.req.GetAllReplyRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.res.CreateReplyResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.res.ReplyResponseDTO;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyController {

  private final ReplyService replyService;

  @GetMapping("/replies/{replyNo}")
  public ResponseEntity<ReplyResponseDTO> getOne(@PathVariable Long replyNo) {
    ReplyDTO response = replyService.getOne(replyNo);

    return new ResponseEntity<>(ReplyResponseDTO.from(response), HttpStatus.OK);
  }

  @GetMapping("/comments/{commentNo}/replies")
  public ResponseEntity<PageResponseDTO<ReplyResponseDTO>> getAll(@PathVariable Long commentNo,
      @Valid GetAllReplyRequestDTO query) {
    PageResponseDTO<ReplyDTO> response = replyService.getAll(commentNo, query.page(),
        query.take(), query.orderBy());
    List<ReplyResponseDTO> data = response.data().stream().map(ReplyResponseDTO::from).toList();

    return new ResponseEntity<>(new PageResponseDTO<>(data, response.meta()), HttpStatus.OK);
  }

  @PostMapping("/comments/{commentNo}/replies")
  public ResponseEntity<CreateReplyResponseDTO> create(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long commentNo, @Valid @RequestBody CreateReplyRequestDTO body) {
    String response = replyService.create(user.userNo(), commentNo, body.content());

    return new ResponseEntity<>(new CreateReplyResponseDTO(response), HttpStatus.CREATED);
  }

  @PatchMapping("/comments/{commentNo}/replies/{replyNo}")
  public ResponseEntity<CreateReplyResponseDTO> updateOne(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long commentNo, @PathVariable Long replyNo,
      @Valid @RequestBody CreateReplyRequestDTO body) {
    String response = replyService.update(user.userNo(), replyNo, body.content());

    return new ResponseEntity<>(new CreateReplyResponseDTO(response), HttpStatus.OK);
  }

  @DeleteMapping("/comments/{commentNo}/replies/{replyNo}")
  public ResponseEntity<Void> deleteOne(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long commentNo, @PathVariable Long replyNo) {
    replyService.delete(user.userNo(), replyNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
