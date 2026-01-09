package kr.modernworld.modernworldv2.member.application.user.dto;

import java.util.List;
import java.util.Map;

public record UserAttendanceDTO(
    String nickname,
    Map<String, List<Integer>> attendance
) {

}
