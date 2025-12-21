package kr.modernworld.modernworldv2.user.domain.like.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.like.Like;

public interface LikeRepository {

  Like save(Like like);

  Optional<Like> findBySenderNoAndReceiverNoForUpdate(Long senderNo, Long receiverNo);

  void delete(Like like);
}
