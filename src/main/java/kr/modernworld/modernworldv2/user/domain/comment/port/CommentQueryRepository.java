package kr.modernworld.modernworldv2.user.domain.comment.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.presentation.comment.dto.res.CommentResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.comment.dto.res.GetCommentResponseDTO;

public interface CommentQueryRepository {

  Optional<CommentResponseDTO> findByNo(Long commentNo);

  Optional<GetCommentResponseDTO> findByNoWithReplyCount(Long commentNo);

  List<GetCommentResponseDTO> findAll(Long userNo, Long skip, Long take, OrderBy orderBy,
      SenderReceiverNoField type);

  Long countByUserNo(Long userNo, SenderReceiverNoField type);
}
