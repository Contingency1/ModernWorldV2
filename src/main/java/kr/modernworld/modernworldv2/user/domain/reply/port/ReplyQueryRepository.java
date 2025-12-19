package kr.modernworld.modernworldv2.user.domain.reply.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.user.presentation.reply.dto.res.ReplyResponseDTO;

public interface ReplyQueryRepository {

  Optional<ReplyResponseDTO> findOne(Long replyNo);

  Long count(Long commentNo);

  List<ReplyResponseDTO> findByCommentNo(Long commentNo, Long skip, Long take, OrderBy orderBy);

}
