package kr.modernworld.modernworldv2.social.domain.comment.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.social.domain.comment.Comment;

public interface CommentRepository {

  Comment save(Comment comment);

  Optional<Comment> findByNoForUpdate(Long commentNo);

}
