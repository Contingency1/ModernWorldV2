package kr.modernworld.modernworldv2.member.presentation.user;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.application.user.OrderByField;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.req.GetUsersRequestDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.res.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/{userNo}")
  public ResponseEntity<UserResponseDTO> getOneUser(@PathVariable Long userNo) {
    UserDTO response = userService.getOne(userNo);

    return new ResponseEntity<>(UserResponseDTO.from(response), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<PageResponseDTO<UserResponseDTO>> getAllUsers(
      @Valid GetUsersRequestDTO query) {
    PageResponseDTO<UserDTO> response = userService.getAll(query.page(), query.take(),
        query.animal(),
        OrderByField.stringToOrderByField(query.orderBy()),
        query.nickname());

    List<UserResponseDTO> data = response.data()
        .stream()
        .map(UserResponseDTO::from)
        .toList();

    return new ResponseEntity<>(new PageResponseDTO<>(data, response.meta()), HttpStatus.OK);
  }

}
