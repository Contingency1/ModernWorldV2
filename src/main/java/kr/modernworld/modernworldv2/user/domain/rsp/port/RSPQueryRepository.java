package kr.modernworld.modernworldv2.user.domain.rsp.port;

import java.time.Instant;
import java.util.List;
import kr.modernworld.modernworldv2.user.presentation.rsp.dto.res.RSPResponseDTO;

public interface RSPQueryRepository {

  List<RSPResponseDTO> findAllByUserNoAndDate(Long userNo, Instant start, Instant end);
}
