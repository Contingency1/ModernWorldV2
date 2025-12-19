package kr.modernworld.modernworldv2.user.application.comment;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.user.application.userachievement.event.UpdateLegendCheckAchievementEvent;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.user.domain.comment.Comment;
import kr.modernworld.modernworldv2.user.domain.comment.port.CommentQueryRepository;
import kr.modernworld.modernworldv2.user.domain.comment.port.CommentRepository;
import kr.modernworld.modernworldv2.user.presentation.comment.dto.req.GetCommentsRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.comment.dto.res.CommentResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.comment.dto.res.GetCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CommentQueryRepository commentQueryRepository;
  private final UserService userService;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional(readOnly = true)
  public PageResponseDTO<GetCommentResponseDTO> getAll(Long userNo, GetCommentsRequestDTO query) {
    Long skip = query.take() * (query.page() - 1);

    List<GetCommentResponseDTO> data =
        commentQueryRepository.findAll(userNo, skip, query.take(), query.orderBy(), query.type());

    Long totalCount = commentQueryRepository.countByUserNo(userNo, query.type());
    Long totalPage = (long) Math.ceil((double) totalCount / query.take());

    PageMetaDTO meta = new PageMetaDTO(query.page(), query.take(), totalCount, totalPage);

    return new PageResponseDTO<>(data, meta);
  }

  @Transactional
  public CommentResponseDTO create(Long senderNo, Long receiverNo, String content) {
    userService.isPresent(receiverNo);

    Comment comment = Comment.init(senderNo, receiverNo, content);

    Comment savedComment = commentRepository.save(comment);

    CommentResponseDTO response = commentQueryRepository.findByNo(savedComment.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));

    String messageForReceiver = String.format("%s님이 방명록을 남겼습니다.",
        response.commentSender().nickname());

    eventPublisher.publishEvent(
        new UpdateLegendCheckAchievementEvent(this, senderNo, LegendField.COMMENT_COUNT));

    eventPublisher.publishEvent(
        new AlarmEvent(this, receiverNo, messageForReceiver, AlarmTitle.COMMENT));

    return response;
  }

  @Transactional
  public CommentResponseDTO update(Long userNo, Long commentNo, String content) {
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

  public GetCommentResponseDTO getOne(Long commentNo) {
    return commentQueryRepository.findByNoWithReplyCount(commentNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));
  }

  public void isPresent(Long commentNo) {
    if (!commentQueryRepository.exists(commentNo)) {
      throw new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND);
    }
  }
}
