package kr.modernworld.modernworldv2.social.domain.neighbor.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.NeighborDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborDTO;

public interface NeighborQueryRepository {

  Boolean isAlreadyNeighbor(Long senderNo, Long receiverNo);

  List<GetNeighborDTO> findAll(Long userNo, Long skip, Long take, OrderBy orderBy,
      Boolean status,
      SenderReceiverNoField senderReceiver);

  Boolean findOneRequestThatStatusIsFalse(Long senderNo, Long receiverNo);

  Optional<NeighborDTO> findOneByNo(Long neighborNo);

  Boolean isPresent(Long neighborNo);

  Long count(Long userNo, Boolean status, SenderReceiverNoField senderReceiver);
}
