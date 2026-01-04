package kr.modernworld.modernworldv2.social.application.like;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.social.application.like.dto.LikeDTO;
import kr.modernworld.modernworldv2.social.application.like.port.LikeQueryRepository;
import kr.modernworld.modernworldv2.social.domain.external.MemberExternalPort;
import kr.modernworld.modernworldv2.social.domain.like.Like;
import kr.modernworld.modernworldv2.social.domain.like.event.LikeCreatedEvent;
import kr.modernworld.modernworldv2.social.domain.like.event.LikeDeletedEvent;
import kr.modernworld.modernworldv2.social.domain.like.port.LikeRepository;
import kr.modernworld.modernworldv2.social.presentation.like.dto.res.get.LikeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final ApplicationEventPublisher eventPublisher;
  private final LikeRepository likeRepository;
  private final LikeQueryRepository likeQueryRepository;
  private final MemberExternalPort memberExternalPort;

  @Transactional(readOnly = true)
  public List<? extends LikeResponseDTO> getAll(
      Long userNo, SenderReceiverNoField type) {

    return likeQueryRepository.findAllByUserNo(userNo, type);
  }

  @Transactional
  public LikeDTO createOne(Long senderNo, Long receiverNo) {
    memberExternalPort.validateUser(receiverNo);

    Boolean exists = likeQueryRepository.existsBySenderNoAndReceiverNo(senderNo, receiverNo);
    if (exists) {
      throw new BusinessException(BusinessErrorCode.LIKE_ALREADY_LIKED);
    }

    Like like;
    try {
      like = Like.init(senderNo, receiverNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.LIKE_CANNOT_LIKE_YOURSELF);
    }

    Like saved = likeRepository.save(like);

    LikeDTO response = likeQueryRepository.findByNo(saved.getNo());

    eventPublisher.publishEvent(
        new LikeCreatedEvent(senderNo, receiverNo, response.sender().nickname()));

    return response;
  }

  @Transactional
  public void deleteOne(Long senderNo, Long receiverNo) {
    Like like = likeRepository.findBySenderNoAndReceiverNoForUpdate(
            senderNo, receiverNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.LIKE_NOT_FOUND));

    try {
      like.validateDeletion(senderNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.LIKE_CANNOT_DELETE_OTHERS);
    }

    likeRepository.delete(like);
    eventPublisher.publishEvent(new LikeDeletedEvent(receiverNo));
  }

  @Transactional(readOnly = true)
  public Boolean getOne(Long senderNo, Long receiverNo) {
    return likeQueryRepository.existsBySenderNoAndReceiverNo(senderNo, receiverNo);
  }
}
