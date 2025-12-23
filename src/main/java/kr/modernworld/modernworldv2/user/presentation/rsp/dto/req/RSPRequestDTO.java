package kr.modernworld.modernworldv2.user.presentation.rsp.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record RSPRequestDTO(
    @Max(3) @Min(1) Integer choice) {

  public RSPRequestDTO {
    if (choice == null) {
      choice = 3;
    }
  }
}
