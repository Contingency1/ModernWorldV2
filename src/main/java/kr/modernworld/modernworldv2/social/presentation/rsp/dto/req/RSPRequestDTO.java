package kr.modernworld.modernworldv2.social.presentation.rsp.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record RSPRequestDTO(@Max(2) @Min(0) Integer choice) {

}
