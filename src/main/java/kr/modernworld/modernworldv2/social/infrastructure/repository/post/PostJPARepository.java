package kr.modernworld.modernworldv2.social.infrastructure.repository.post;

import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.PostJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostJPARepository extends JpaRepository<PostJPAEntity, Long> {

}
