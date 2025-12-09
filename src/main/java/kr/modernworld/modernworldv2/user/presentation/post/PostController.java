package kr.modernworld.modernworldv2.user.presentation.post;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.user.application.post.PostService;
import kr.modernworld.modernworldv2.user.infrastructure.repository.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.user.presentation.post.dto.req.CreateOnePostRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.post.dto.req.GetAllPostsRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostResponseDTO;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping("/my/posts")
  public ResponseEntity<List<PostResponseDTO>> getAll(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid GetAllPostsRequestDTO query
  ) {
    List<PostResponseDTO> response = postService.getAll(user.userNo(), query);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/my/posts/{postNo}")
  public ResponseEntity<PostResponseDTO> getOne(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long postNo) {
    PostResponseDTO response = postService.getOne(user.userNo(), postNo);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/{userNo}/posts")
  public ResponseEntity<PostResponseDTO> create(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable("userNo") Long receiverNo, @Valid CreateOnePostRequestDTO body) {
    PostResponseDTO response = postService.create(user.userNo(), receiverNo, body.content());

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @DeleteMapping("/my/posts/{postNo}")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long postNo) {
    postService.deleteByNo(user.userNo(), postNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
