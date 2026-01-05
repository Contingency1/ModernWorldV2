package kr.modernworld.modernworldv2.social.domain.reply.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.social.domain.reply.Reply;

public interface ReplyRepository {

  Reply save(Reply reply);

  Optional<Reply> findOneForUpdate(Long replyNo);

}
