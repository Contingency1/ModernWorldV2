package kr.modernworld.modernworldv2.user.presentation.alarm;

import jakarta.validation.Valid;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.user.application.alarm.AlarmService;
import kr.modernworld.modernworldv2.user.presentation.alarm.dto.req.AlarmRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.alarm.dto.res.AlarmResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/my/alarms")
public class AlarmController {

  private final AlarmService alarmService;

  @GetMapping()
  public ResponseEntity<PageResponseDTO<AlarmResponseDTO>> getAllAlarms(
      @AuthenticationPrincipal TokenUserInfoDTO user,
      @Valid AlarmRequestDTO query
  ) {
    PageResponseDTO<AlarmResponseDTO> response = alarmService.getAllAlarms(user.userNo(), query);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{alarmNo}")
  public ResponseEntity<Void> updateAlarmStatus(
      @AuthenticationPrincipal TokenUserInfoDTO user, @PathVariable Long alarmNo) {
    alarmService.updateAlarmRead(user.userNo(), alarmNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{alarmNo}")
  public ResponseEntity<Void> deleteOneAlarm(@AuthenticationPrincipal TokenUserInfoDTO user,
      @PathVariable Long alarmNo) {
    alarmService.deleteOne(user.userNo(), alarmNo);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
