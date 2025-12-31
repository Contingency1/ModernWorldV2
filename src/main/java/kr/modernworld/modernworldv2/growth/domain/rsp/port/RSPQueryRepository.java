package kr.modernworld.modernworldv2.growth.domain.rsp.port;

import java.time.Instant;
import java.util.List;
import kr.modernworld.modernworldv2.growth.presentation.rsp.dto.res.RSPResponseDTO;

public interface RSPQueryRepository {

  List<RSPResponseDTO> findAllByUserNoAndDate(Long userNo, Instant start, Instant end);
}
