package kr.modernworld.modernworldv2.social.application.like;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.growth.application.userachievement.event.DecrementLegendEvent;
import kr.modernworld.modernworldv2.growth.application.userachievement.event.IncrementLegendAndCheckAchievementEvent;
import kr.modernworld.modernworldv2.growth.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.social.domain.like.Like;
import kr.modernworld.modernworldv2.social.domain.like.port.LikeQueryRepository;
import kr.modernworld.modernworldv2.social.domain.like.port.LikeRepository;
import kr.modernworld.modernworldv2.social.presentation.like.dto.res.CreateLikeResponseDTO;
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
  private final UserService userService;

  @Transactional(readOnly = true)
  public List<? extends LikeResponseDTO> getAll(Long userNo, SenderReceiverNoField type) {

    return likeQueryRepository.findAllByUserNo(userNo, type);
  }

  @Transactional
  public CreateLikeResponseDTO createOne(Long senderNo, Long receiverNo) {
    userService.isPresent(receiverNo);

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

    CreateLikeResponseDTO response = likeQueryRepository.findByNo(saved.getNo());

    String eventMessage = String.format("%s님이 좋아요를 눌렀습니다.", response.userLikeSenderNo().nickname());

    eventPublisher.publishEvent(new AlarmEvent(this, receiverNo, eventMessage, AlarmTitle.LIKE));
    eventPublisher.publishEvent(new IncrementLegendAndCheckAchievementEvent(this, receiverNo,
        LegendField.LIKE_COUNT));

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
    eventPublisher.publishEvent(new DecrementLegendEvent(this, receiverNo, LegendField.LIKE_COUNT));
  }

  @Transactional(readOnly = true)
  public Boolean getOne(Long senderNo, Long receiverNo) {
    return likeQueryRepository.existsBySenderNoAndReceiverNo(senderNo, receiverNo);
  }
}
