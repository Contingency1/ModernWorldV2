package kr.modernworld.modernworldv2.social.application.post.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.social.application.post.dto.PostDTO;

public interface PostQueryRepository {

  List<PostDTO> findAll(Long userNo, SenderReceiverNoField senderReceiverNoField,
      OrderBy orderBy);

  Optional<PostDTO> findOne(Long userNo, Long postNo);
}
