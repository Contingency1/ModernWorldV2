package kr.modernworld.modernworldv2.member.presentation.user.dto.res;

import java.util.List;
import java.util.Map;
import kr.modernworld.modernworldv2.member.application.user.dto.UserAttendanceDTO;

public record UserAttendanceResponseDTO(String nickname, Map<String, List<Integer>> attendance) {

  public static UserAttendanceResponseDTO from(UserAttendanceDTO dto) {
    return new UserAttendanceResponseDTO(dto.nickname(), dto.attendance());
  }
}
