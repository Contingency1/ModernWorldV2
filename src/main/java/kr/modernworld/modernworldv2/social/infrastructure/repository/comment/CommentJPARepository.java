package kr.modernworld.modernworldv2.social.infrastructure.repository.comment;

import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.CommentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJPARepository extends JpaRepository<CommentJPAEntity, Long> {

}
