package kr.modernworld.modernworldv2.social.application.rsp.port;

import java.time.Instant;
import java.util.List;
import kr.modernworld.modernworldv2.social.application.rsp.dto.GetRSPDTO;

public interface RSPQueryRepository {

  List<GetRSPDTO> findAllByUserNoAndDate(Long userNo, Instant start, Instant end);
}
