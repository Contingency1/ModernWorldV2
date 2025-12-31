package kr.modernworld.modernworldv2.social.application.reply;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.growth.application.userachievement.event.IncrementLegendAndCheckAchievementEvent;
import kr.modernworld.modernworldv2.social.application.comment.CommentService;
import kr.modernworld.modernworldv2.social.domain.reply.Reply;
import kr.modernworld.modernworldv2.social.domain.reply.port.ReplyQueryRepository;
import kr.modernworld.modernworldv2.social.domain.reply.port.ReplyRepository;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.req.GetAllReplyRequestDTO;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.res.CreateResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.reply.dto.res.ReplyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

  private final ReplyRepository replyRepository;
  private final ReplyQueryRepository replyQueryRepository;
  private final CommentService commentService;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional(readOnly = true)
  public ReplyResponseDTO getOne(Long replyNo) {
    return replyQueryRepository.findOne(replyNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.REPLY_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public PageResponseDTO<ReplyResponseDTO> getAll(Long commentNo, GetAllReplyRequestDTO query) {
    commentService.isPresent(commentNo);

    Long take = query.take();
    Long page = query.page();
    OrderBy orderBy = query.orderBy();

    Long skip = take * (page - 1);
    Long totalCount = replyQueryRepository.count(commentNo);

    Long totalPage = (long) Math.ceil((double) totalCount / take);

    List<ReplyResponseDTO> data = replyQueryRepository.findByCommentNo(commentNo, skip, take,
        orderBy);
    PageMetaDTO meta = new PageMetaDTO(page, take, totalCount, totalPage);

    return new PageResponseDTO<>(data, meta);
  }

  @Transactional
  public CreateResponseDTO create(Long userNo, Long commentNo, String content) {
    commentService.isPresent(commentNo);

    Reply reply = Reply.init(commentNo, userNo, content);

    Reply saved = replyRepository.save(reply);

    eventPublisher.publishEvent(
        new IncrementLegendAndCheckAchievementEvent(this, userNo, LegendField.COMMENT_COUNT));

    return new CreateResponseDTO(saved.getContent());
  }

  @Transactional
  public CreateResponseDTO update(Long userNo, Long replyNo, String content) {
    Reply reply = replyRepository.findOneForUpdate(replyNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.REPLY_NOT_FOUND));

    commentService.isPresent(reply.getCommentNo());

    try {
      reply.updateContent(userNo, content);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.REPLY_USER_NOT_HAVE);
    }

    Reply saved = replyRepository.save(reply);

    return new CreateResponseDTO(saved.getContent());
  }

  @Transactional
  public void delete(Long userNo, Long replyNo) {
    Reply reply = replyRepository.findOneForUpdate(replyNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.REPLY_NOT_FOUND));

    try {
      reply.updateDeletedAt(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.REPLY_USER_NOT_HAVE);
    }

    replyRepository.save(reply);
  }
}
