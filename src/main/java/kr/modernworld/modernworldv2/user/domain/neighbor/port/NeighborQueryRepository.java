package kr.modernworld.modernworldv2.user.domain.neighbor.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.neighbor.dto.res.NeighborResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.neighbor.dto.res.get.GetNeighborResponseDTO;

public interface NeighborQueryRepository {

  Boolean isAlreadyNeighbor(Long senderNo, Long receiverNo);

  List<GetNeighborResponseDTO> findAll(Long userNo, Long skip, Long take, OrderBy orderBy,
      Boolean status,
      SenderReceiverNoField senderReceiver);

  Boolean findOneRequestThatStatusIsFalse(Long senderNo, Long receiverNo);

  Optional<NeighborResponseDTO> findOneByNo(Long neighborNo);

  Boolean isPresent(Long neighborNo);

  Long count(Long userNo, Boolean status, SenderReceiverNoField senderReceiver);
}
