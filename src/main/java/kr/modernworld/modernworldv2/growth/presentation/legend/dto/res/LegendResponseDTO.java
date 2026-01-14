package kr.modernworld.modernworldv2.growth.presentation.legend.dto.res;

import kr.modernworld.modernworldv2.growth.application.legend.dto.LegendDTO;

public record LegendResponseDTO(
    Long userNo,
    Long attendanceCount,
    Long commentCount,
    Long itemCount,
    Long presentCount,
    Long likeCount,
    Long RSPWinCount
) {

  public static LegendResponseDTO from(LegendDTO dto) {
    return new LegendResponseDTO(
        dto.userNo(),
        dto.attendanceCount(),
        dto.commentCount(),
        dto.itemCount(),
        dto.presentCount(),
        dto.likeCount(),
        dto.rspWinCount()
    );
  }
}
