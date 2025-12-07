package kr.modernworld.modernworldv2.user.domain.port.post;

import java.util.List;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostResponseDTO;

public interface PostQueryRepository {

  List<PostResponseDTO> findAll(Long userNo, SenderReceiverNoField senderReceiverNoField,
      OrderBy orderBy);

  PostResponseDTO findOne(Long postNo);
}
