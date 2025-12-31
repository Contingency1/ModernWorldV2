package kr.modernworld.modernworldv2.social.domain.post.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.social.domain.post.Post;

public interface PostRepository {

  Post save(Post post);

  Optional<Post> findOneByUserNoAndPostNoForUpdate(Long userNo, Long postNo);
}
