package kr.modernworld.modernworldv2.social.application.comment;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.growth.application.userachievement.event.IncrementLegendAndCheckAchievementEvent;
import kr.modernworld.modernworldv2.notification.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.application.comment.dto.CommentDTO;
import kr.modernworld.modernworldv2.social.application.comment.dto.GetCommentDTO;
import kr.modernworld.modernworldv2.social.application.comment.port.CommentQueryRepository;
import kr.modernworld.modernworldv2.social.domain.comment.Comment;
import kr.modernworld.modernworldv2.social.domain.comment.port.CommentRepository;
import kr.modernworld.modernworldv2.social.domain.external.MemberExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final MemberExternalPort memberExternalPort;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional(readOnly = true)
  public PageResponseDTO<GetCommentDTO> getAll(Long userNo,
      Long page, Long take,
      OrderBy orderBy, SenderReceiverNoField type) {
    Long skip = take * (page - 1);

    List<GetCommentDTO> data =
        commentQueryRepository.findAll(userNo, skip, take, orderBy, type);

    Long totalCount = commentQueryRepository.countByUserNo(userNo, type);
    Long totalPage = (long) Math.ceil((double) totalCount / take);

    PageMetaDTO meta = new PageMetaDTO(page, take, totalCount, totalPage);

    return new PageResponseDTO<>(data, meta);
  }

  @Transactional
  public CommentDTO create(Long senderNo, Long receiverNo, String content) {
    memberExternalPort.validateUser(receiverNo);

    Comment comment = Comment.init(senderNo, receiverNo, content);

    Comment savedComment = commentRepository.save(comment);

    CommentDTO response = commentQueryRepository.findByNo(savedComment.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));

    String messageForReceiver = String.format("%s님이 방명록을 남겼습니다.",
        response.commentSender().nickname());

    eventPublisher.publishEvent(
        new IncrementLegendAndCheckAchievementEvent(this, senderNo, LegendField.COMMENT_COUNT));

    eventPublisher.publishEvent(
        new AlarmEvent(this, receiverNo, messageForReceiver, AlarmTitle.COMMENT));

    return response;
  }

  @Transactional
  public CommentDTO update(Long userNo, Long commentNo, String content) {
    Comment comment = commentRepository.findByNoForUpdate(commentNo)
        .orElseThrow(() -> new BusinessException(
            BusinessErrorCode.COMMENT_NOT_FOUND));

    try {
      comment.updateContent(userNo, content);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.COMMENT_NOT_A_SENDER);
    }

    commentRepository.save(comment);

    return commentQueryRepository.findByNo(commentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));
  }

  @Transactional
  public void updateToBeDeleted(Long userNo, Long commentNo) {
    Comment comment = commentRepository.findByNoForUpdate(commentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));

    try {
      comment.updateDeletedAt(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.COMMENT_NOT_A_SENDER);
    }

    commentRepository.save(comment);
  }

  public GetCommentDTO getOne(Long commentNo) {
    return commentQueryRepository.findByNoWithReplyCount(commentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));
  }

  public void isPresent(Long commentNo) {
    if (!commentQueryRepository.exists(commentNo)) {
      throw new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND);
    }
  }
}
