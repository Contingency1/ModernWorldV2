package kr.modernworld.modernworldv2.user.infrastructure.repository.user.socialtoken;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.TokenJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialTokenJPARepository extends JpaRepository<TokenJPAEntity, Long> {

  TokenJPAEntity findByUser_No(Long userNo);
}
