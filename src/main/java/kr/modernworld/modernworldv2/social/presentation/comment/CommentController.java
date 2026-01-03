package kr.modernworld.modernworldv2.social.presentation.comment;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.social.application.comment.CommentService;
import kr.modernworld.modernworldv2.social.application.comment.dto.CommentDTO;
import kr.modernworld.modernworldv2.social.application.comment.dto.GetCommentDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.req.CreateCommentRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.req.GetCommentsRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.res.CommentResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.res.GetCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @GetMapping("/comments/{commentNo}")
  public ResponseEntity<GetCommentResponseDTO> getOne(@PathVariable Long commentNo) {
    return new ResponseEntity<>(
        GetCommentResponseDTO.from(commentService.getOne(commentNo)),
        HttpStatus.OK);
  }

  @PostMapping("/users/{userNo}/comments")
  public ResponseEntity<CommentResponseDTO> create(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable("userNo") Long receiverNo,
      @Valid CreateCommentRequestDTO body) {
    CommentDTO response = commentService.create(user.userNo(), receiverNo,
        body.content());

    return new ResponseEntity<>(CommentResponseDTO.from(response), HttpStatus.CREATED);
  }

  @GetMapping("/users/{userNo}/comments")
  public ResponseEntity<PageResponseDTO<GetCommentResponseDTO>> getAll(
      @PathVariable Long userNo,
      GetCommentsRequestDTO query) {
    PageResponseDTO<GetCommentDTO> response = commentService.getAll(
        userNo, query.page(),
        query.take(), query.orderBy(), query.type());

    List<GetCommentResponseDTO> data = response.data().stream().map(GetCommentResponseDTO::from)
        .toList();

    return new ResponseEntity<>(new PageResponseDTO<>(data, response.meta()), HttpStatus.OK);
  }

  @PatchMapping("/users/my/comments/{commentNo}")
  public ResponseEntity<CommentResponseDTO> updateOne(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long commentNo, @Valid CreateCommentRequestDTO body) {
    CommentDTO response = commentService.update(user.userNo(), commentNo,
        body.content());

    return new ResponseEntity<>(CommentResponseDTO.from(response), HttpStatus.OK);
  }

  @DeleteMapping("/users/my/comments/{commentNo}")
  public ResponseEntity<Void> deleteOne(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long commentNo) {
    commentService.updateToBeDeleted(user.userNo(), commentNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
