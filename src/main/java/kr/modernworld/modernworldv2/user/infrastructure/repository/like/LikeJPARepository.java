package kr.modernworld.modernworldv2.user.infrastructure.repository.like;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.LikeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeJPARepository extends JpaRepository<LikeJPAEntity, Long> {

}
