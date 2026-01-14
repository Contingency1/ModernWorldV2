package kr.modernworld.modernworldv2.member.presentation.user;

import jakarta.validation.Valid;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.application.user.OrderByField;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.member.application.user.dto.UserAttendanceDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDescriptionDTO;
import kr.modernworld.modernworldv2.member.application.user.dto.UserNicknameDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.req.CreateUserNicknameRequestDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.req.GetUsersRequestDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.req.UpdateUserAttendanceRequestDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.req.UpdateUserDescriptionRequestDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.res.UserAttendanceResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.res.UserDescriptionResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.res.UserNicknameResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.user.dto.res.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        OrderByField.stringToOrderByField(query.orderByField()),
        query.nickname());

    List<UserResponseDTO> data = response.data()
        .stream()
        .map(UserResponseDTO::from)
        .toList();

    return new ResponseEntity<>(new PageResponseDTO<>(data, response.meta()), HttpStatus.OK);
  }

  @GetMapping("/my/attendance")
  public ResponseEntity<UserAttendanceResponseDTO> getAttendance(
      @AuthenticationPrincipal TokenUserInfoDTO user) {
    UserAttendanceDTO response = userService.getUserAttendance(user.userNo());

    return new ResponseEntity<>(UserAttendanceResponseDTO.from(response), HttpStatus.OK);
  }

  @PostMapping("/my/nickname")
  public ResponseEntity<UserNicknameResponseDTO> createUserNickname(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid @RequestBody CreateUserNicknameRequestDTO body) {
    UserNicknameDTO response = userService.createUserNickname(user.userNo(), body.nickname());

    return new ResponseEntity<>(UserNicknameResponseDTO.from(response), HttpStatus.CREATED);
  }

  @PatchMapping("/my/attendance")
  public ResponseEntity<UserAttendanceResponseDTO> updateAttendance(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid @RequestBody UpdateUserAttendanceRequestDTO body) {
    UserAttendanceDTO response = userService.updateAttendance(user.userNo(),
        body.stickerNo());

    return new ResponseEntity<>(UserAttendanceResponseDTO.from(response), HttpStatus.OK);
  }

  @PutMapping("/my/description")
  public ResponseEntity<UserDescriptionResponseDTO> updateDescription(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid @RequestBody UpdateUserDescriptionRequestDTO body) {
    UserDescriptionDTO response = userService.updateDescription(user.userNo(),
        body.description());

    return new ResponseEntity<>(
        new UserDescriptionResponseDTO(response.userNo(), response.description()), HttpStatus.OK);
  }

}
