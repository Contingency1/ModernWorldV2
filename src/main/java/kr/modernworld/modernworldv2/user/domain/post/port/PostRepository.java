package kr.modernworld.modernworldv2.user.domain.post.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.post.Post;

public interface PostRepository {

  Post save(Post post);

  Optional<Post> findOneByUserNoAndPostNoForUpdate(Long userNo, Long postNo);
}
