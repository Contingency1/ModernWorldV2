package kr.modernworld.modernworldv2.user.domain.post.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostResponseDTO;

public interface PostQueryRepository {

  List<PostResponseDTO> findAll(Long userNo, SenderReceiverNoField senderReceiverNoField,
      OrderBy orderBy);

  Optional<PostResponseDTO> findOne(Long userNo, Long postNo);
}
