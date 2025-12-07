package kr.modernworld.modernworldv2.user.application.post;

import java.util.List;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.port.post.PostQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.post.PostRepository;
import kr.modernworld.modernworldv2.user.domain.post.Post;
import kr.modernworld.modernworldv2.user.presentation.post.dto.req.GetAllPostsRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostQueryRepository postQueryRepository;
  private final PostRepository postRepository;

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

      return postQueryRepository.findOne(postNo);
    }

    return postQueryRepository.findOne(postNo);
  }

  @Transactional
  public PostResponseDTO create(Long senderNo, Long receiverNo, String content) {
    Post save = postRepository.save(Post.init(senderNo, receiverNo, content));
    // ======================== 알람 추가 =============================================

    return postQueryRepository.findOne(save.getNo());
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
