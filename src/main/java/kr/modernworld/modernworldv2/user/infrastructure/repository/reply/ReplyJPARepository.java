package kr.modernworld.modernworldv2.user.infrastructure.repository.reply;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.ReplyJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyJPARepository extends JpaRepository<ReplyJPAEntity, Long> {

}
