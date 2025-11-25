package kr.modernworld.modernworldv2.user.infrastructure.repository.present;

import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.PresentJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentJPARepository extends JpaRepository<PresentJPAEntity, Long> {

}
