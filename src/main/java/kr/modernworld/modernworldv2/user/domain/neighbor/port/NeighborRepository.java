package kr.modernworld.modernworldv2.user.domain.neighbor.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.neighbor.Neighbor;

public interface NeighborRepository {

  Neighbor save(Neighbor neighbor);

  Long delete(Long neighborNo, Long userNo);

  Optional<Neighbor> findOneThatStatusIsFalseForUpdate(Long senderNo,
      Long receiverNo);

  Optional<Neighbor> findByNoAndReceiverNoForUpdate(Long neighborNo, Long receiverNo);

}
