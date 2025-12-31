package kr.modernworld.modernworldv2.social.application.neighbor;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.social.domain.neighbor.Neighbor;
import kr.modernworld.modernworldv2.social.domain.neighbor.port.NeighborQueryRepository;
import kr.modernworld.modernworldv2.social.domain.neighbor.port.NeighborRepository;
import kr.modernworld.modernworldv2.user.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.presentation.neighbor.dto.req.GetNeighborsRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res.NeighborResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res.get.GetNeighborResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NeighborService {

  private final NeighborQueryRepository neighborQueryRepository;
  private final NeighborRepository neighborRepository;

  private final UserService userService;

  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional(readOnly = true)
  public PageResponseDTO<GetNeighborResponseDTO> getAll(Long userNo, GetNeighborsRequestDTO query) {
    Long skip = (query.page() - 1) * query.take();
    Long totalCount = neighborQueryRepository.count(userNo, query.status(), query.type());
    Long totalPage = (long) Math.ceil((double) totalCount / query.take());

    List<GetNeighborResponseDTO> data = neighborQueryRepository.findAll(userNo, skip, query.take(),
        query.orderBy(), query.status(),
        query.type());

    PageMetaDTO meta = new PageMetaDTO(query.page(), query.take(), totalCount, totalPage);

    return new PageResponseDTO<>(data, meta);
  }

  @Transactional
  public NeighborResponseDTO create(Long senderNo, Long receiverNo) {
    Boolean alreadyNeighbor = neighborQueryRepository.isAlreadyNeighbor(senderNo, receiverNo);

    if (alreadyNeighbor) {
      throw new BusinessException(BusinessErrorCode.NEIGHBOR_ALREADY_NEIGHBOR);
    }

    Neighbor neighbor;

    try {
      neighbor = Neighbor.init(senderNo, receiverNo);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.NEIGHBOR_CANNOT_INVITE_TO_YOURSELF,
          " reason: " + e.getMessage());
    }

    userService.isPresent(receiverNo);

    Boolean myRequest = neighborQueryRepository.findOneRequestThatStatusIsFalse(
        senderNo, receiverNo);

    // 이미 요청을 보낸 경우 예외
    if (myRequest) {
      throw new BusinessException(BusinessErrorCode.NEIGHBOR_ALREADY_REQUESTED,
          " reason: You already requested to this user.");
    }

    Optional<Neighbor> opponentRequest = neighborRepository.findOneThatStatusIsFalseForUpdate(
        senderNo, receiverNo);

    // 이미 상대가 요청을 보낸경우 자동수락
    if (opponentRequest.isPresent()) {
      return setNeighborStatusTrue(opponentRequest.get());
    }

    Neighbor saved = neighborRepository.save(neighbor);

    NeighborResponseDTO response = neighborQueryRepository.findOneByNo(saved.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));

    Long receiverNumber = response.neighborReceiverNo().no();
    String senderName = response.neighborSenderNo().nickname();

    String messageForReceiver = String.format("%s님에게 이웃 요청이 왔습니다.", senderName);
    applicationEventPublisher.publishEvent(
        new AlarmEvent(this, receiverNumber, messageForReceiver, AlarmTitle.NEIGHBOR));

    return response;
  }

  @Transactional
  public NeighborResponseDTO update(Long receiverNo, Long neighborNo) {
    Neighbor neighbor = neighborRepository.findByNoAndReceiverNoForUpdate(neighborNo, receiverNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));

    try {
      neighbor.makeStatusTrue();
    } catch (Exception e) {
      throw new BusinessException(BusinessErrorCode.NEIGHBOR_ALREADY_RECEIVED);
    }

    Neighbor saved = neighborRepository.save(neighbor);

    return neighborQueryRepository.findOneByNo(saved.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));
  }

  @Transactional
  public void delete(Long neighborNo, Long userNo) {
    Long deletedCount = neighborRepository.delete(neighborNo, userNo);

    if (deletedCount > 0) {
      return;
    }

    Boolean isPresent = neighborQueryRepository.isPresent(neighborNo);

    if (isPresent) {
      throw new BusinessException(BusinessErrorCode.NEIGHBOR_CANNOT_DELETE);
    }

    throw new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND);
  }

  private NeighborResponseDTO setNeighborStatusTrue(Neighbor neighbor) {
    neighbor.makeStatusTrue();
    Neighbor saved = neighborRepository.save(neighbor);

    NeighborResponseDTO neighborDetail = neighborQueryRepository.findOneByNo(saved.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));

    Long senderNo = neighborDetail.neighborSenderNo().no();
    Long receiverNo = neighborDetail.neighborReceiverNo().no();
    String senderName = neighborDetail.neighborSenderNo().nickname();
    String receiverName = neighborDetail.neighborReceiverNo().nickname();

    String messageForSender = String.format("%s님과 이웃이 되었습니다.", receiverName);
    String messageForReceiver = String.format("%s님과 이웃이 되었습니다.", senderName);

    applicationEventPublisher.publishEvent(
        new AlarmEvent(this, senderNo, messageForSender,
            AlarmTitle.NEIGHBOR));

    applicationEventPublisher.publishEvent(
        new AlarmEvent(this, receiverNo, messageForReceiver,
            AlarmTitle.NEIGHBOR));

    return neighborDetail;
  }
}
