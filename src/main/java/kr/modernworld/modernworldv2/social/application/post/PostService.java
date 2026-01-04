package kr.modernworld.modernworldv2.social.application.post;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.notification.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.application.post.dto.PostDTO;
import kr.modernworld.modernworldv2.social.application.post.port.PostQueryRepository;
import kr.modernworld.modernworldv2.social.domain.external.MemberExternalPort;
import kr.modernworld.modernworldv2.social.domain.post.Post;
import kr.modernworld.modernworldv2.social.domain.post.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostQueryRepository postQueryRepository;
  private final PostRepository postRepository;
  private final MemberExternalPort memberExternalPort;
  private final ApplicationEventPublisher eventPublisher;

  public List<PostDTO> getAll(Long userNo, SenderReceiverNoField senderReceiverNoField,
      OrderBy orderBy) {
    return postQueryRepository.findAll(userNo, senderReceiverNoField, orderBy);
  }

  @Transactional
  public PostDTO getOne(Long userNo, Long postNo) {
    Post post = postRepository.findOneByUserNoAndPostNoForUpdate(userNo, postNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.POST_NOT_FOUND));

    if (post.getReceiverNo().equals(userNo) && !post.getCheck()) {
      post.makeCheckTrue(userNo);
      postRepository.save(post);

      return postQueryRepository.findOne(userNo, postNo)
          .orElseThrow(() -> new BusinessException(BusinessErrorCode.POST_NOT_FOUND));
    }

    return postQueryRepository.findOne(userNo, postNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.POST_NOT_FOUND));
  }

  @Transactional
  public PostDTO create(Long senderNo, Long receiverNo, String content) {
    memberExternalPort.validateUser(receiverNo);

    Post post;

    try {
      post = Post.init(senderNo, receiverNo, content);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(
          BusinessErrorCode.POST_CANNOT_POST_TO_YOURSELF, ", reason: " + e.getMessage());
    }

    Post save = postRepository.save(post);

    // ===================================== 나중에 익명 고칠것 ==============================
    String alarmMessage = String.format("%s님이 쪽지를 보내셨습니다.", "익명");
    // ===================================== 나중에 익명 고칠것 ==============================
    eventPublisher.publishEvent(new AlarmEvent(this, receiverNo, alarmMessage, AlarmTitle.POST));
    return postQueryRepository.findOne(senderNo, save.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.POST_NOT_FOUND));
  }

  @Transactional
  public void deleteByNo(Long userNo, Long postNo) {
    Post post = postRepository.findOneByUserNoAndPostNoForUpdate(userNo, postNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.POST_NOT_FOUND));

    if (post.getSenderNo().equals(userNo)) {
      post.deleteFromSender(userNo);
      postRepository.save(post);
      return;
    }

    post.deleteFromReceiver(userNo);
    postRepository.save(post);
  }

}
