package kr.modernworld.modernworldv2.social.application.neighbor;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.notification.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.NeighborDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborDTO;
import kr.modernworld.modernworldv2.social.domain.external.MemberExternalPort;
import kr.modernworld.modernworldv2.social.domain.neighbor.Neighbor;
import kr.modernworld.modernworldv2.social.domain.neighbor.port.NeighborQueryRepository;
import kr.modernworld.modernworldv2.social.domain.neighbor.port.NeighborRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NeighborService {

  private final NeighborQueryRepository neighborQueryRepository;
  private final NeighborRepository neighborRepository;
  private final MemberExternalPort memberExternalPort;

  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional(readOnly = true)
  public PageResponseDTO<GetNeighborDTO> getAll(Long userNo, Long page, Long take,
      OrderBy orderBy, Boolean status, SenderReceiverNoField type) {
    Long skip = (page - 1) * take;
    Long totalCount = neighborQueryRepository.count(userNo, status, type);
    Long totalPage = (long) Math.ceil((double) totalCount / take);

    List<GetNeighborDTO> data = neighborQueryRepository.findAll(userNo, skip, take,
        orderBy, status,
        type);

    PageMetaDTO meta = new PageMetaDTO(page, take, totalCount, totalPage);

    return new PageResponseDTO<>(data, meta);
  }

  @Transactional
  public NeighborDTO create(Long senderNo, Long receiverNo) {
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

    memberExternalPort.validateUser(receiverNo);

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

    NeighborDTO response = neighborQueryRepository.findOneByNo(saved.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));

    Long receiverNumber = response.receiver().no();
    String senderName = response.sender().nickname();

    String messageForReceiver = String.format("%s님에게 이웃 요청이 왔습니다.", senderName);
    applicationEventPublisher.publishEvent(
        new AlarmEvent(this, receiverNumber, messageForReceiver, AlarmTitle.NEIGHBOR));

    return response;
  }

  @Transactional
  public NeighborDTO update(Long receiverNo, Long neighborNo) {
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

  private NeighborDTO setNeighborStatusTrue(Neighbor neighbor) {
    neighbor.makeStatusTrue();
    Neighbor saved = neighborRepository.save(neighbor);

    NeighborDTO neighborDetail = neighborQueryRepository.findOneByNo(saved.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));

    Long senderNo = neighborDetail.sender().no();
    Long receiverNo = neighborDetail.receiver().no();
    String senderName = neighborDetail.sender().nickname();
    String receiverName = neighborDetail.receiver().nickname();

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
