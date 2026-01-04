package kr.modernworld.modernworldv2.social.application.comment.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.social.application.comment.dto.CommentDTO;
import kr.modernworld.modernworldv2.social.application.comment.dto.GetCommentDTO;

public interface CommentQueryRepository {

  Optional<CommentDTO> findByNo(Long commentNo);

  Optional<GetCommentDTO> findByNoWithReplyCount(Long commentNo);

  List<GetCommentDTO> findAll(Long userNo, Long skip, Long take, OrderBy orderBy,
      SenderReceiverNoField type);

  Long countByUserNo(Long userNo, SenderReceiverNoField type);

  Boolean exists(Long commentNo);
}
