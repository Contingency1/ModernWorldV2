package kr.modernworld.modernworldv2.social.application.reply.port;

import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.social.application.reply.dto.ReplyDTO;

public interface ReplyQueryRepository {

  Optional<ReplyDTO> findOne(Long replyNo);

  Long count(Long commentNo);

  List<ReplyDTO> findByCommentNo(Long commentNo, Long skip, Long take, OrderBy orderBy);

}
