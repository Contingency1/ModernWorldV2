package kr.modernworld.modernworldv2.social.presentation.neighbor;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.NeighborService;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborDTO;
import kr.modernworld.modernworldv2.social.presentation.neighbor.dto.req.GetNeighborsRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res.NeighborResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res.get.GetNeighborResponseDTO;
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
public class NeighborController {

  private final NeighborService neighborService;

  @GetMapping("/my/neighbors")
  public ResponseEntity<PageResponseDTO<GetNeighborResponseDTO>> getMyNeighbors(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid GetNeighborsRequestDTO query) {
    PageResponseDTO<GetNeighborDTO> response = neighborService.getAll(user.userNo(),
        query.page(), query.take(), query.orderBy(), query.status(), query.type());

    List<GetNeighborResponseDTO> data = response.data().stream().map(GetNeighborResponseDTO::from)
        .toList();
    
    return new ResponseEntity<>(new PageResponseDTO<>(data, response.meta()), HttpStatus.OK);
  }

  @PostMapping("/{userNo}/neighbors")
  public ResponseEntity<NeighborResponseDTO> create(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable("userNo") Long receiver) {
    return new ResponseEntity<>(
        NeighborResponseDTO.from(neighborService.create(user.userNo(), receiver)),
        HttpStatus.CREATED);
  }

  @PatchMapping("/my/neighbors/{neighborNo}")
  public ResponseEntity<NeighborResponseDTO> patchNeighbor(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable Long neighborNo) {
    return new ResponseEntity<>(
        NeighborResponseDTO.from(neighborService.update(user.userNo(), neighborNo)), HttpStatus.OK);
  }

  @DeleteMapping("/my/neighbors/{neighborNo}")
  public ResponseEntity<Void> deleteNeighbor(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long neighborNo) {

    neighborService.delete(neighborNo, user.userNo());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
