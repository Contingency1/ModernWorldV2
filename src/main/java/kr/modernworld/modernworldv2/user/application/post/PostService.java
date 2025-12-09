package kr.modernworld.modernworldv2.user.application.post;

import java.util.List;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.user.domain.post.Post;
import kr.modernworld.modernworldv2.user.domain.post.port.PostQueryRepository;
import kr.modernworld.modernworldv2.user.domain.post.port.PostRepository;
import kr.modernworld.modernworldv2.user.presentation.post.dto.req.GetAllPostsRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostQueryRepository postQueryRepository;
  private final PostRepository postRepository;
  private final UserService userService;
  private final ApplicationEventPublisher eventPublisher;

  public List<PostResponseDTO> getAll(Long userNo, GetAllPostsRequestDTO query) {
    return postQueryRepository.findAll(userNo, query.senderReceiverNoField(), query.orderBy());
  }

  @Transactional
  public PostResponseDTO getOne(Long userNo, Long postNo) {
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
  public PostResponseDTO create(Long senderNo, Long receiverNo, String content) {
    userService.isPresent(receiverNo);

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
