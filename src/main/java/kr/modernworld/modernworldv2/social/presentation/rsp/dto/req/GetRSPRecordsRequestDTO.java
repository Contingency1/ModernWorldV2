package kr.modernworld.modernworldv2.social.presentation.rsp.dto.req;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record GetRSPRecordsRequestDTO(
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date
) {

  public GetRSPRecordsRequestDTO {
    if (date == null) {
      date = LocalDate.now();
    }
  }
}